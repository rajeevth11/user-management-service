package org.tvm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.tvm.domain.UserDTO;
import org.tvm.domain.UserResponseDTO;
import org.tvm.jpa.User;

import java.util.List;

@Mapper
public interface CriteriaMapper
{
    CriteriaMapper INSTANCE = Mappers.getMapper( CriteriaMapper.class );

    User mapUser( UserDTO userDTO );

    UserDTO mapUser( User userDTO );

    List<UserResponseDTO> mapUserList( List<User> users );


}
