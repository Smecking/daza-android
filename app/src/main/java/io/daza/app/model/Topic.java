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

public class Topic extends Model {

    private String type;            // 类型
    private String source_program;  // 来源程序 = ['wordpress', 'jekyll', 'ghost', 'hexo', 'typecho', 'farbox']
    private String source_link;     // 来源链接
    private String name;            // 名称
    private String image_url;       // 图片网址
    private String description;     // 描述

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource_program() {
        return source_program;
    }

    public void setSource_program(String source_program) {
        this.source_program = source_program;
    }

    public String getSource_link() {
        return source_link;
    }

    public void setSource_link(String source_link) {
        this.source_link = source_link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
