package com.fanteng.util;

import java.util.HashMap;
import java.util.Map;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

public class FastDFSUtil {

	private static final ClassPathResource RESOURCE = new ClassPathResource("fdfs_client.conf");

	public static String upload(MultipartFile file) throws Exception {
		ClientGlobal.init(RESOURCE.getClassLoader().getResource("fdfs_client.conf").getPath());
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);

		String filename = file.getOriginalFilename();
		String suffix = filename.substring(filename.lastIndexOf("."));
		String size = String.valueOf(file.getSize());

		NameValuePair[] metas = new NameValuePair[4];
		metas[0] = new NameValuePair("filename", filename);
		metas[1] = new NameValuePair("suffix", suffix);
		metas[2] = new NameValuePair("size", size);
		metas[3] = new NameValuePair("uploadTime", String.valueOf(System.currentTimeMillis()));

		String path = storageClient1.upload_file1(file.getBytes(), suffix, metas);
		return path;
	}

	public static byte[] download(String fileId) throws Exception {
		ClientGlobal.init(RESOURCE.getClassLoader().getResource("fdfs_client.conf").getPath());
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);

		byte[] file = storageClient1.download_file1(fileId);
		return file;
	}

	public static int delete(String fileId) throws Exception {
		ClientGlobal.init(RESOURCE.getClassLoader().getResource("fdfs_client.conf").getPath());
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);

		int code = storageClient1.delete_file1(fileId);
		return code;
	}

	public static Map<String, String> getFileInfo(String fileId) throws Exception {
		ClientGlobal.init(RESOURCE.getClassLoader().getResource("fdfs_client.conf").getPath());
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);

		Map<String, String> data = new HashMap<>(0);
		NameValuePair[] valuePairs = storageClient1.get_metadata1(fileId);
		if (valuePairs != null && valuePairs.length > 0) {
			for (NameValuePair nameValuePair : valuePairs) {
				data.put(nameValuePair.getName(), nameValuePair.getValue());
			}
		}
		return data;
	}

}
