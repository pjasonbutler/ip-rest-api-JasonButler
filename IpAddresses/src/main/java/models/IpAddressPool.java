package models;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.net.util.SubnetUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IpAddressPool {

	public static final String FILE_NAME = "addresses.json";

	private Map<String, IpProperties> pool = new LinkedHashMap<String, IpProperties>();

	public IpAddressPool() {
		// empty constructor
	}

	public static Object listAddresses() throws IOException, ParseException {
		return getActivePool().pool;
	}

	public static IpAddressPool createPool(String cidr) throws IOException, IllegalArgumentException {
		IpAddressPool addressPool = new IpAddressPool();
		SubnetUtils utils = new SubnetUtils(cidr);
		String[] allIps = utils.getInfo().getAllAddresses();
		
		for (String ip : allIps) {
			addressPool.pool.put(ip, new IpProperties(ip, "available"));
		}

		ObjectMapper mapper = new ObjectMapper();
		FileWriter file = new FileWriter(FILE_NAME);
		String json = mapper.writeValueAsString(addressPool.pool);
		file.write(json);
		file.flush();
		file.close();

		return addressPool;
	}

	public static IpAddressPool getActivePool() throws IOException, ParseException {
		Map<String, IpProperties> map = new LinkedHashMap<String, IpProperties>();
		ObjectMapper mapper = new ObjectMapper();

		FileReader fr = new FileReader(FILE_NAME);
		JSONParser parser = new JSONParser();

		map = mapper.readValue(parser.parse(fr).toString(), new TypeReference<LinkedHashMap<String, IpProperties>>() {
		});
		IpAddressPool addressPool = new IpAddressPool();
		addressPool.pool = map;
		return addressPool;
	}

	public IpProperties setIpStatus(String address, String status) throws IOException {

		if (!pool.containsKey(address)) {
			return new IpProperties(address, "Address not found in IP pool.");
		}

		ObjectMapper mapper = new ObjectMapper();

		pool.put(address, new IpProperties(address, status));
		String json = mapper.writeValueAsString(pool);

		FileWriter file = new FileWriter(FILE_NAME);
		file.write(json);
		System.out.println(json);
		file.flush();
		file.close();

		return pool.get(address);

	}
}
