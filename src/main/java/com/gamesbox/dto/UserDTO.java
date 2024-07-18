package com.gamesbox.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {
   @NotNull(message="is required")
   @Size(min=1, message="is required")
   private String username;

   @NotNull(message="is required")
   @Size(min=1, message="is required")
   private String password;

   @NotNull(message = "is required")
   @Size(min = 1, message = "is required")
   @Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
   private String email;

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }



}
