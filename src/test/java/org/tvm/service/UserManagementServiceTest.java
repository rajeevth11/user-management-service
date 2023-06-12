package org.tvm.service;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tvm.controller.repository.UserRepository;
import org.tvm.domain.ResponseWrapper;
import org.tvm.domain.UserDTO;
import org.tvm.domain.UserResponseDTO;
import org.tvm.jpa.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest
{

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserManagementService userManagementService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks( this );
    }

    @Test
    void createUser()
    {
        when( userRepository.findByUserName( any( String.class ) ) ).thenReturn( new ArrayList<>() );
        when( userRepository.save( any( User.class ) ) ).thenReturn( new User() );
        ResponseEntity<ResponseWrapper> response = userManagementService.createUser( new User( null, "rajeevth", "Rajeev", "Thennahewa", "rajeev11@gmail.com", "+464848373743" ) );
        assertTrue( response.getStatusCode().equals( HttpStatus.CREATED ) );
    }

    @Test
    void createUser_user_exist_with_same_username()
    {
        List<User> userList = new ArrayList<>();
        userList.add( new User( 1, "rajeevth", "Rajeev", "Thennahewa", "rajeev11@gmail.com", "+464848373743" ) );
        when( userRepository.findByUserName( any( String.class ) ) ).thenReturn( userList );
        ResponseEntity<ResponseWrapper> response = userManagementService.createUser( new User( null, "rajeevth", "Rajeev", "Thennahewa", "rajeev11@gmail.com", "+464848373743" ) );
        assertTrue( response.getStatusCode().equals( HttpStatus.BAD_REQUEST ) );
    }


    @Test
    void getUserById()
    {
        Optional<User> user = Optional.of( new User( 1, "rajeevth", "Rajeev", "Thennahewa", "rajeev11@gmail.com", "+464848373743" ) );
        when( userRepository.findById( 1 ) ).thenReturn( user );
        ResponseEntity<ResponseWrapper<UserDTO>> responseWrapperResponseEntity = userManagementService.getUserById( 1 );
        assertEquals( responseWrapperResponseEntity.getBody().getData().size(), 1 );
        assertEquals( ( responseWrapperResponseEntity.getBody().getData().get( 0 ) ).getUserName(), "rajeevth" );
        assertTrue( responseWrapperResponseEntity.getStatusCode().equals( HttpStatus.OK ) );
    }

    @Test
    void getUserById_Not_Found()
    {
        Optional<User> user = Optional.empty();
        when( userRepository.findById( 1 ) ).thenReturn( user );
        ResponseEntity<ResponseWrapper<UserDTO>> responseWrapperResponseEntity = userManagementService.getUserById( 1 );
        assertTrue( responseWrapperResponseEntity.getStatusCode().equals( HttpStatus.NOT_FOUND ) );
    }

    @Test
    void getUserList()
    {
        List<User> users = new ArrayList<>();
        users.add( new User( 1, "rajeevth", "Rajeev", "Thennahewa", "rajeev11@gmail.com", "+464848373743" ) );
        users.add( new User( 2, "shami", "Shami", "Thennahewa", "sami@gmail.com", "+464848373743" ) );
        Page<User> pagedResult = new PageImpl<>( users, PageRequest.of( 1, 5, Sort.by( Sort.Direction.ASC, "userId" ) ), 2 );

        when( userRepository.findAll( any( Pageable.class ) ) ).thenReturn( pagedResult );

        ResponseEntity<ResponseWrapper<List<UserResponseDTO>>> responseWrapperResponseEntity = userManagementService.getUserList( 1, 5 );
        assertTrue( responseWrapperResponseEntity.getStatusCode().equals( HttpStatus.OK ) );
        assertEquals( responseWrapperResponseEntity.getBody().getData().get( 0 ).size(), 2 );
    }

    @Test
    void updateUser()
    {
        Optional<User> optional = Optional.of( new User( 1, "rajeevth", "Rajeev", "Thennahewa", "rajeev11@gmail.com", "+464848373743" ) );
        when( userRepository.findById( any( Integer.class ) ) ).thenReturn( optional );
        ResponseEntity<ResponseWrapper> response = userManagementService.updateUser( 1, new User( 1, "rajeevth", "Kamal", "Thennahewa", "rajeev11@gmail.com", "+464848373743" ) );
        assertTrue( response.getStatusCode().equals( HttpStatus.OK ) );

    }

    @Test
    void deleteUser()
    {
        Optional<User> optional = Optional.of( new User( 1, "rajeevth", "Rajeev", "Thennahewa", "rajeev11@gmail.com", "+464848373743" ) );
        when( userRepository.findById( any( Integer.class ) ) ).thenReturn( optional );
        ResponseEntity<ResponseWrapper> response = userManagementService.deleteUser( 1 );
        assertTrue( response.getStatusCode().equals( HttpStatus.OK ) );

    }

    @Test
    void deleteUser_when_user_not_found()
    {
        Optional<User> optional = Optional.empty();
        when( userRepository.findById( any( Integer.class ) ) ).thenReturn( optional );
        ResponseEntity<ResponseWrapper> response = userManagementService.deleteUser( 1 );
        assertTrue( response.getStatusCode().equals( HttpStatus.NOT_FOUND ) );

    }
}