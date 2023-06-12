package org.tvm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tvm.controller.repository.UserRepository;
import org.tvm.domain.ResponseWrapper;
import org.tvm.domain.UserDTO;
import org.tvm.domain.UserResponseDTO;
import org.tvm.jpa.User;
import org.tvm.mapper.CriteriaMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserManagementService
{
    @Autowired
    private UserRepository userRepository;


    /**
     * Create User entity.
     *
     * @param user of type User
     * @return UserDTO
     */
    public ResponseEntity<ResponseWrapper> createUser( User user )
    {
        ResponseEntity<ResponseWrapper> userResponseEntity = null;
        try
        {
            List<User> returnUsers = userRepository.findByUserName( user.getUserName() );
            if( !returnUsers.isEmpty() )
            {
                userResponseEntity = new ResponseEntity( new ResponseWrapper( ResponseWrapper.ERROR, "User already exist with the same user name." ), HttpStatus.BAD_REQUEST );
            }
            User UserResult = userRepository.save( user );
            userResponseEntity = new ResponseEntity( new ResponseWrapper( ResponseWrapper.SUCCESS, "User created successfully." ), HttpStatus.CREATED );
        }
        catch( Exception e )
        {
            userResponseEntity = new ResponseEntity( new ResponseWrapper( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return userResponseEntity;
    }


    /**
     * Retrieve user by id.
     *
     * @param id of type Integer
     * @return
     */
    public ResponseEntity<ResponseWrapper<UserDTO>> getUserById( Integer id )
    {
        ResponseEntity<ResponseWrapper<UserDTO>> responseWrapperResponseEntity = null;
        try
        {
            Optional<User> returnObj = userRepository.findById( id );
            if( returnObj.isPresent() )
            {
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<UserDTO>( ResponseWrapper.SUCCESS, "User retrieved successfully.", CriteriaMapper.INSTANCE.mapUser( returnObj.get() ) ), HttpStatus.OK );
            }
            else
            {
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<UserDTO>( ResponseWrapper.ERROR, "User not found for Id : " + id ), HttpStatus.NOT_FOUND );
            }
        }
        catch( Exception e )
        {
            responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<UserDTO>( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return responseWrapperResponseEntity;
    }

    /**
     * Retrieve List of users.
     *
     * @return
     */
    public ResponseEntity<ResponseWrapper<List<UserResponseDTO>>> getUserList(Integer page, Integer size)
    {
        ResponseEntity<ResponseWrapper<List<UserResponseDTO>>> responseWrapperResponseEntity = null;
        try
        {
            Page<User> pagedResult = userRepository.findAll( PageRequest.of( page, size, Sort.by( Sort.Direction.ASC, "userId" ) ) );
            if( pagedResult.hasContent() )
            {
                List<User> userList = pagedResult.stream().collect( Collectors.toList() );
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<List<UserResponseDTO>>( ResponseWrapper.SUCCESS, "User retrieved successfully.", CriteriaMapper.INSTANCE.mapUserList( userList ) ), HttpStatus.OK );

            }
            else
            {
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<UserDTO>( ResponseWrapper.ERROR, "Users not found." ), HttpStatus.NOT_FOUND );
            }
        }
        catch( Exception e )
        {
            responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper<UserDTO>( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return responseWrapperResponseEntity;
    }

    /**
     * Update User by id.
     *
     * @param id of type Integer
     * @param user of type User
     * @return
     */
    public ResponseEntity<ResponseWrapper> updateUser( Integer id, User user )
    {
        ResponseEntity<ResponseWrapper> responseWrapperResponseEntity = null;
        try
        {
            Optional<User> returnObj = userRepository.findById( id );
            if( returnObj.isPresent() )
            {
                userRepository.save( user );
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper( ResponseWrapper.SUCCESS, "User updated successfully." ), HttpStatus.OK );
            }
            else
            {
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper( ResponseWrapper.ERROR, "User not found for Id : " + id ), HttpStatus.NOT_FOUND );
            }
        }
        catch( Exception e )
        {
            responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return responseWrapperResponseEntity;
    }

    /**
     * Delete User entity.
     *
     * @param id of type Integer
     * @return ResponseEntity
     */
    public ResponseEntity<ResponseWrapper> deleteUser( Integer id )
    {
        ResponseEntity<ResponseWrapper> responseWrapperResponseEntity = null;
        try
        {
            Optional<User> returnObj = userRepository.findById( id );
            if( returnObj.isPresent() )
            {
                userRepository.deleteById( id );
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper( ResponseWrapper.SUCCESS, "User deleted successfully." ), HttpStatus.OK );
            }
            else
            {
                responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper( ResponseWrapper.ERROR, "User not found for Id : " + id ), HttpStatus.NOT_FOUND );
            }
        }
        catch( Exception e )
        {
            responseWrapperResponseEntity = new ResponseEntity( new ResponseWrapper( ResponseWrapper.ERROR, "Error while processing the request : " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return responseWrapperResponseEntity;
    }




}
