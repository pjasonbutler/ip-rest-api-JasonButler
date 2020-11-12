package com.butler.jason.IpAddresses;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import models.IpAddressPool;

@RestController
public class IpController {

	public IpController() {
		// empty constructor
	}

	@GetMapping("/makelist")
	public ResponseEntity<String> getIpAddress(@RequestParam String cidr) {
		try {
			IpAddressPool.createPool(cidr);
			return ResponseEntity.ok("List Created");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem writing to file.");
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid CIDR notation.");
		}
	}

	@GetMapping("/getlist")
	public Object retrieveList() {
		try {
			return IpAddressPool.listAddresses();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to read file");
		}
	}

	@PostMapping("/acquireip")
	public ResponseEntity<Object> acquireAddress(@RequestParam String address) {
		try {
			return ResponseEntity.ok(IpAddressPool.getActivePool().setIpStatus(address, "acquired"));
		} catch (IOException e1) {
			e1.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to write to file.");
		} catch (ParseException e1) {
			e1.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to read file");
		}
	}

	@PostMapping("/releaseip")
	public ResponseEntity<Object> releaseAddress(@RequestParam String address) {
		try {
			return ResponseEntity.ok(IpAddressPool.getActivePool().setIpStatus(address, "available"));
		} catch (IOException e1) {
			e1.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to write to file.");
		} catch (ParseException e1) {
			e1.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to read file");
		}
	}

}
