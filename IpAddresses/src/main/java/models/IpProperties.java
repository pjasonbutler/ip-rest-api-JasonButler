package models;

public class IpProperties {
	
	private String status;
	private String address;

	public IpProperties() {
		//empty constructor
	}
	
	public IpProperties(String address, String status) {
		this.address = address;
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
}
