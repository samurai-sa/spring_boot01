package com.example.comments.forms;
 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
 
import lombok.Getter;
import lombok.Setter;
 
@Getter
@Setter
public class CommentsForm {
 
  @NotNull
  @Size(min=1, max=20)
  private String name;
 
  @NotNull
  @Size(min=1, max=100)
  private String body;
 
  public String toString() {
    return "Comments(Name: " + this.name + ", Body: " + this.body + ")";
  }
}
