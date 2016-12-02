/**
 * Copyright (C) 2015 JianyingLi <lijy91@foxmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.daza.app.model;

import android.text.TextUtils;

import com.google.gson.Gson;

public class Model {

    /**
     * 将Model转换为Json字符串 *
     *
     * @return
     */
    public String toJSONString() {
        Gson gson = new Gson();
        // 转换为Json字符串时将忽略用transient修饰的字段
        return gson.toJson(this);
    }

    public static <T extends Model> T parseObject(final String json, Class<T> clazz) {
        // Json字符串为空，则返回null
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    private int id;
    private String deleted_at;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

}
