package ru.rsatu.common;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class BaseResponse {

    String type;
    String message;

}
