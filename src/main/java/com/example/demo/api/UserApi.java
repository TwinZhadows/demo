package com.example.demo.api;

import com.example.demo.exception.BaseException;
import com.example.demo.exception.EmailException;
import com.example.demo.exception.FileException;
import com.example.demo.exception.UserException;
import com.example.demo.logic.UserApiLogic;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/test")

public class UserApi {

	@Autowired
	private UserApiLogic logic; // intantly give reusable instance of UserApiLogic class without creating new instance

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws BaseException {
		return ResponseEntity.ok(logic.login(request));
	}

	@PostMapping("/register")
	public ResponseEntity<RegisterResponse>  register(@RequestBody RegisterRequest request) throws UserException, EmailException {

		RegisterResponse response = logic.register(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadProfilePicture(@RequestPart MultipartFile file) throws FileException {
		String response = logic.uploadProfilePicture(file);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/profile")
	public ResponseEntity<UserProfile> getUserProfile() throws BaseException {
		UserProfile response = logic.getUserProfile();
		return ResponseEntity.ok(response);
	}

	@PutMapping("/profile")
	public ResponseEntity<UserProfile> updateUserProfile(@RequestBody UpdateUserProfileRequest request) throws BaseException {
		UserProfile response = logic.updateUserProfile(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/activate")
	public ResponseEntity<ActivateResponse> activateUser(@RequestBody ActivateRequest request) throws BaseException {
		ActivateResponse response = logic.activate(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/reactivate")
	public ResponseEntity<Void> reactivateUser(@RequestBody ReactivationRequest request) throws BaseException {
		logic.reactivate(request);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/refresh-token")
	public ResponseEntity<String> refreshToken() throws BaseException {
		String response = logic.refreshToken();
		return ResponseEntity.ok(response);
	}

//	@DeleteMapping("/delete")
//	public ResponseEntity<Void> deleteMyAccount() throws UserException {
//		logic.deleteMyAccount();
//		return ResponseEntity.ok().build();
//	}
}
