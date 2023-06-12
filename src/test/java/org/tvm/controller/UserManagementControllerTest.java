package org.tvm.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tvm.domain.ResponseWrapper;
import org.tvm.domain.UserDTO;
import org.tvm.domain.UserResponseDTO;
import org.tvm.jpa.User;
import org.tvm.service.UserManagementService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserManagementControllerTest
{

    @Mock
    private UserManagementService userManagementService;

    @InjectMocks
    private UserManagementController userManagementController;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks( this );
    }

    @Test
    void createUser()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes( new ServletRequestAttributes( request ) );

        ResponseEntity<ResponseWrapper> userResponse = new ResponseEntity( new ResponseWrapper( ResponseWrapper.SUCCESS, "User created successfully." ), HttpStatus.CREATED );
        when( userManagementService.createUser( any( User.class ) ) ).thenReturn( userResponse );

        ResponseEntity<ResponseWrapper> response = userManagementController.createUser( new UserDTO( 1, "rajeevth", "Rajeev", "Thennahewa", "rajeev@gmail.com", "+464848373743" ) );
        assertThat( response.getStatusCodeValue() ).isEqualTo( 201 );
    }

    @Test
    void getUserList()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes( new ServletRequestAttributes( request ) );
        UserResponseDTO userResponseDTO1 = new UserResponseDTO( 1, "rajeevth", "rajeev@gmail.com" );
        UserResponseDTO userResponseDTO2 = new UserResponseDTO( 2, "shami141", "shami@gmail.com" );
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        userResponseDTOList.add( userResponseDTO1 );
        userResponseDTOList.add( userResponseDTO2 );
        ResponseEntity<ResponseWrapper<List<UserResponseDTO>>> userResponse = new ResponseEntity( new ResponseWrapper( ResponseWrapper.SUCCESS, "User List retrieved successfully.", userResponseDTOList ), HttpStatus.OK );

        when( userManagementService.getUserList( any( Integer.class ), any( Integer.class ) ) ).thenReturn( userResponse );

        ResponseEntity<ResponseWrapper<List<UserResponseDTO>>> response = userManagementController.getUserList( 1, 5 );
        assertThat( response.getStatusCodeValue() ).isEqualTo( 200 );
        assertThat( response.getBody().getData().size() ).isEqualTo( 2 );
    }

    @Test
    void getUserById()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes( new ServletRequestAttributes( request ) );
        UserDTO userDTO = new UserDTO( 1, "rajeevth", "Rajeev", "Thennahewa", "rajeev@gmail.com", "+464848373743" );
        ResponseEntity<ResponseWrapper<UserDTO>> responseEntity = new ResponseEntity( new ResponseWrapper( ResponseWrapper.SUCCESS, "User created successfully.", userDTO ), HttpStatus.OK );

        when( userManagementService.getUserById( any( Integer.class ) ) ).thenReturn( responseEntity );

        ResponseEntity<ResponseWrapper<UserDTO>> response = userManagementController.getUserById( 1 );
        assertThat( response.getStatusCodeValue() ).isEqualTo( 200 );
        assertThat( response.getBody().getData().get( 0 ).getUserName() ).isEqualTo( "rajeevth" );

    }

    @Test
    void updateUser()
    {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes( new ServletRequestAttributes( request ) );

        ResponseEntity<ResponseWrapper> responseEntity = new ResponseEntity( new ResponseWrapper( ResponseWrapper.SUCCESS, "User updated successfully." ), HttpStatus.OK );

        when( userManagementService.updateUser( any( Integer.class ), any( User.class ) ) ).thenReturn( responseEntity );

        UserDTO userDTO = new UserDTO( 1, "rajeevth", "Rajeev", "Thennahewa", "rajeev11@gmail.com", "+464848373743" );
        ResponseEntity<ResponseWrapper> response = userManagementController.updateUser( 1, userDTO );
        assertThat( response.getStatusCodeValue() ).isEqualTo( 200 );

    }

    @Test
    void deleteUser()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes( new ServletRequestAttributes( request ) );
        ResponseEntity<ResponseWrapper> responseEntity = new ResponseEntity( new ResponseWrapper( ResponseWrapper.SUCCESS, "User deleted successfully." ), HttpStatus.OK );

        when( userManagementService.deleteUser( any( Integer.class ) ) ).thenReturn( responseEntity );

        ResponseEntity<ResponseWrapper> response = userManagementController.deleteUser( 1 );
        assertThat( response.getStatusCodeValue() ).isEqualTo( 200 );
    }
}