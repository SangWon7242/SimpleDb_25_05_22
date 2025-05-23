package com.sbs.article;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class Article {
  private long id;
  private LocalDateTime createDate;
  private LocalDateTime modifiedDate;
  private String subject;
  private String content;
  private boolean isBlind;
}
