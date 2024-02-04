package com.ApiWiz.Task.Management.ResponseDto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListDtoResponse {
    private List<Long> userIds ;
}
