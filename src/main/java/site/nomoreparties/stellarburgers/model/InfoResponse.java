package site.nomoreparties.stellarburgers.model;

import lombok.Getter;

@Getter
public class InfoResponse {
    private boolean success;
    private String message;

    public InfoResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public InfoResponse() {
    }
}
