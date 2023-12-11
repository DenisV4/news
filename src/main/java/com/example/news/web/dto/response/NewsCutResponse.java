package com.example.news.web.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@FieldNameConstants
public class NewsCutResponse extends AbstractNewsResponse {

    private int commentCount;
}
