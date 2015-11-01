package itrexgroup.testtask.services;

import itrexgroup.testtask.entities.ExifInformation;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;

@Service
public class ExifService {

	@Autowired
	Logger log;

	public ExifInformation getExif(File file) throws ImageProcessingException, IOException, ParseException {
		ExifInformation exif = readSetExifInfo(file);
		double[] latLong = getGeoLocationPlace(file);
		exif.setLatitude(latLong[0]);
		exif.setLongitude(latLong[1]);
		return exif;
	}

	private double[] getGeoLocationPlace(File file)	throws ImageProcessingException, IOException {
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		double[] location = { 0.0, 0.0 };
		Collection<GpsDirectory> gpsDirectories = metadata
				.getDirectoriesOfType(GpsDirectory.class);
		if (gpsDirectories != null) {
			log.info("null in gpsDirectories");
			for (GpsDirectory gpsDirectory : gpsDirectories) {
				GeoLocation geoLocation = gpsDirectory.getGeoLocation();
				if (geoLocation != null && !geoLocation.isZero()) {
					location[0] = geoLocation.getLatitude();
					location[1] = geoLocation.getLongitude();
				}
			}
		}
		return location;
	}

	private ExifInformation readSetExifInfo(File File) throws ParseException, ImageProcessingException, IOException {
		ExifInformation exif = new ExifInformation();
		Metadata metadata = ImageMetadataReader.readMetadata(File);
		for (com.drew.metadata.Directory directory : metadata.getDirectories()) {
			for (com.drew.metadata.Tag tag : directory.getTags()) {
				if (tag.getTagName().equals("Date/Time")) {
					SimpleDateFormat sdf = new SimpleDateFormat("YYYY:MM:dd hh:mm:ss");
					exif.setDateAndTime(sdf.parse(tag.getDescription()));
					if (exif.getDateAndTime() == null)
						exif.setDateAndTime(sdf.parse("0000:00:00 00:00:00"));
				}
				if (tag.getTagName().equals("Compression Type")) {
					exif.setCompression(tag.getDescription());
				}
				if (tag.getTagName().equals("Exposure Time")) {
					exif.setExposureTime(tag.getDescription());
				}
				if (tag.getTagName().equals("Model")) {
					exif.setModel(tag.getDescription());
				}
				if (tag.getTagName().equals("Make")) {
					exif.setManufacturer(tag.getDescription());
				}
				if (tag.getTagName().equals("Exif Version")) {
					exif.setExifVersion(tag.getDescription());
				}
				if (tag.getTagName().equals("User Comment")) {
					exif.setDescription(tag.getDescription());
				}
			}
		}
		return exif;
	}
}