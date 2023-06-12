package org.tvm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tvm.domain.ResponseWrapper;
import org.tvm.domain.UserDTO;
import org.tvm.domain.UserResponseDTO;
import org.tvm.mapper.CriteriaMapper;
import org.tvm.service.UserManagementService;

import java.util.List;

/**
 * This class will handle the all the requests for the User Management service.
 *
 * @Author : rajeevth11@gmail.com
 */
@RestController
@RequestMapping(value = "user-management-service/api")
@Api("UserManagementController - REST APIs to manage Users CRUD operations")
public class UserManagementController
{
    @Autowired
    private UserManagementService userManagementService;

    @ApiOperation(value = "Create User.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully created."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.POST}, value = "/users", produces = "application/json")
    public ResponseEntity<ResponseWrapper> createUser( @RequestBody(required = true) UserDTO user )
    {
        return userManagementService.createUser( CriteriaMapper.INSTANCE.mapUser( user ) );
    }

    @ApiOperation(value = "Get all the users.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.GET}, value = "/users", produces = "application/json")
    public ResponseEntity<ResponseWrapper<List<UserResponseDTO>>> getUserList( @RequestAttribute(required = true) Integer page, @RequestAttribute(required = true) Integer size )
    {
        return userManagementService.getUserList( page, size );
    }

    @ApiOperation(value = "Retrieve User entity.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.GET}, value = "/users/{id}", produces = "application/json")
    public ResponseEntity<ResponseWrapper<UserDTO>> getUserById( @ApiParam(value = "User ID") @PathVariable Integer id )
    {
        return userManagementService.getUserById( id );
    }

    @ApiOperation(value = "Update User entity.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully Updated."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.PUT}, value = "/users/{id}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> updateCompany( @ApiParam(value = "User ID") @PathVariable Integer id
            , @RequestBody(required = true) UserDTO user )
    {
        return userManagementService.updateUser( id, CriteriaMapper.INSTANCE.mapUser( user ) );
    }

    @ApiOperation(value = "Delete User entity.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully deleted."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 408, message = "Timeout"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "All required permissions not granted"),
            @ApiResponse(code = 405, message = "Method Not Allowed")})
    @RequestMapping(method = {RequestMethod.DELETE}, value = "/users/{id}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> deleteUser( @ApiParam(value = "User ID") @PathVariable Integer id )
    {
        return userManagementService.deleteUser( id );
    }

}
