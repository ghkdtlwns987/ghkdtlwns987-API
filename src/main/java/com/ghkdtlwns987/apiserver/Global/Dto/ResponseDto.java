package com.ghkdtlwns987.apiserver.Global.Dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseDto<T> {
    private boolean success;
    @JsonIgnore
    private HttpStatus status;
    private T data;
    private List<String> errorMessages;

    public static <T> ResponseDtoBuilder<T> builder() {
        return new ResponseDtoBuilder();
    }

    @JsonGetter
    public int getStatus() {
        return this.status.value();
    }

    public boolean isSuccess() {
        return this.success;
    }

    public T getData() {
        return this.data;
    }

    public List<String> getErrorMessages() {
        return this.errorMessages;
    }

    public ResponseDto() {
    }

    public ResponseDto(boolean success, HttpStatus status, T data, List<String> errorMessages) {
        this.success = success;
        this.status = status;
        this.data = data;
        this.errorMessages = errorMessages;
    }

    public static class ResponseDtoBuilder<T> {
        private boolean success;
        private HttpStatus status;
        private T data;
        private List<String> errorMessages;

        ResponseDtoBuilder() {
        }

        public ResponseDtoBuilder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public ResponseDtoBuilder<T> status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ResponseDtoBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseDtoBuilder<T> errorMessages(List<String> errorMessages) {
            this.errorMessages = errorMessages;
            return this;
        }

        public ResponseDto<T> build() {
            return new ResponseDto(this.success, this.status, this.data, this.errorMessages);
        }

        public String toString() {
            return "ResponseDto.ResponseDtoBuilder(success=" + this.success + ", status=" + this.status + ", data=" + this.data + ", errorMessages=" + this.errorMessages + ")";
        }
    }
}
