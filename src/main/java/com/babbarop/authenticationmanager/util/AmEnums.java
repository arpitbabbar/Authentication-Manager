package com.babbarop.authenticationmanager.util;

public interface AmEnums {

    enum StatusRequestType{
        ACTIVE("active"),BLOCKED("blocked"),DELETED("deleted");

        private String name;

        StatusRequestType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
