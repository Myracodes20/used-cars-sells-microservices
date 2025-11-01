package usedcarsproject.inventoryservice.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResource<T> {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;

    public ApiResource(){
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResource(T data){
        this();
        this.data = data;
    }

    public ApiResource(ApiError error){
        this();
        this.error = error;
    }
}
