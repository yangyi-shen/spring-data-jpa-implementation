package com.yyshen.spring_data_jpa_implementation;

import java.util.Objects;

class ItemResource {
    private String status;
    private Item content;

    ItemResource(String status, Item content) {
        this.status = status;
        this.content = content;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Item getContent() {
        return this.content;
    }

    public void setContent(Item content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof ItemResource)) {
            return false;
        }
        ItemResource that = (ItemResource) object;
        return Objects.equals(this.status, that.status) && Objects.equals(this.content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, content);
    }

    @Override
    public String toString() {
        return "ItemResource[status=" + this.status + ", content=" + this.content + "]";
    }
}
