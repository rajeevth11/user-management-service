package org.tvm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO
{
    private Integer userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String telephoneNumber;
}
