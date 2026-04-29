package net.mochinekoserver.guard_villager.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.mochinekoserver.guard_villager.Main;
import net.mochinekoserver.guard_villager.status.FileType;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class JsonManager {

    private static final Map<FileType, JsonManager> memory_json_map = new HashMap<>();

    private JsonElement raw_element;
    private final FileType type;
    private final Gson gson;

    private JsonManager(@Nonnull FileType type) {
        this.type = type;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public JsonManager() {
        throw new UnsupportedOperationException("JsonManager#getInstance(FileType)");
    }

    public static JsonManager getInstance(@Nonnull FileType fileType) {
        if (!memory_json_map.containsKey(fileType)) {
            var instance = new JsonManager(fileType);
            memory_json_map.put(fileType, instance);
            instance.createJson();
        }
        return memory_json_map.get(fileType);
    }

    /**
     * メモリー上からJSONを取得する
     * @return {@link JsonElement}として返す
     */
    public JsonElement getRawElement() {
        if (raw_element == null) updateJson();
        return raw_element;
    }

    /**
     *　デシリアライズしたJSONとして返す
     */
    public DeserializedJson getDeserializedJson() {
        return gson.fromJson(getRawElement(), type.getDeserializedClass());
    }

    /**
     * ファイルからJsonを直接取得する
     * @return {@link JsonElement}として返す
     */
    public JsonElement getFileRawElement() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(getFile().toPath()), StandardCharsets.UTF_8))) {
            return gson.fromJson(reader, JsonElement.class);
        }
    }

    /**
     * @return 現在のJSONがどこにあるかを、ファイルとして返す
     */
    public File getFile() {
        return new File(Main.getPlugin(Main.class).getDataFolder(), type.getName());
    }

    /**
     * メモリー上のJSONを更新する。
     */
    public void updateJson() {
        try {
            if (!getFile().exists()) createJson(); //無い場合は作成
            if (raw_element == null) {
                raw_element = getFileRawElement();
            }
            memory_json_map.put(type, this);

            try (BufferedWriter write = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(getFile().toPath()), StandardCharsets.UTF_8))) {
                write.write(gson.toJson(raw_element));
                Bukkit.getLogger().info(getFile().getName() + "の更新に成功しました。");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSONを作成する関数。
     * @return ファイルの作成に成功した場合 もしくは 既にファイルが存在する場合 は trueを返し、失敗した場合は false を返す。
     */
    public boolean createJson() {
        boolean status = false;
        if (!getFile().exists()) {
            try (InputStream in = Main.class.getResourceAsStream("/" + type.getName())) {
                Files.copy(in, getFile().toPath());
                updateJson();
                status = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            status = true;
        }
        return status;
    }

}
