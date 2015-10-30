package itrexgroup.testtask.services;

import itrexgroup.testtask.entities.ExifInformation;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

	public ExifInformation getExif(File file)
			throws ImageProcessingException, IOException, ParseException {
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		ExifInformation exif = new ExifInformation();
		exif.setDescription("not set");
		exif.setLatitude(0);
		exif.setLongitude(0);

		Collection<GpsDirectory> gpsDirectories = metadata
				.getDirectoriesOfType(GpsDirectory.class);
		if (gpsDirectories != null) {
			log.info("null in gpsDirectories");
			for (GpsDirectory gpsDirectory : gpsDirectories) {
				GeoLocation geoLocation = gpsDirectory.getGeoLocation();
				if (geoLocation != null && !geoLocation.isZero()) {
					exif.setLatitude(geoLocation.getLatitude());
					exif.setLongitude(geoLocation.getLongitude());

				}
			}
		}

		for (com.drew.metadata.Directory directory : metadata.getDirectories()) {
			for (com.drew.metadata.Tag tag : directory.getTags()) {
				if (tag.getTagName().equals("Date/Time")) {
					SimpleDateFormat sdf = new SimpleDateFormat("YYYY:MM:dd hh:mm:ss");
					exif.setDateAndTime(sdf.parse(tag.getDescription()));
					if (exif.getDateAndTime() == null)
						exif.setDateAndTime(sdf.parse("0000:00:00 00:00:00"));
				}
			}
		}

		for (com.drew.metadata.Directory directory : metadata.getDirectories()) {
			for (com.drew.metadata.Tag tag : directory.getTags()) {
				if (tag.getTagName().equals("Compression Type")) {
					exif.setCompression(tag.getDescription());
					if (exif.getCompression() == null)
						exif.setCompression("not set");
				}
			}
		}

		for (com.drew.metadata.Directory directory : metadata.getDirectories()) {
			for (com.drew.metadata.Tag tag : directory.getTags()) {
				if (tag.getTagName().equals("Exposure Time")) {
					exif.setExposureTime(tag.getDescription());
					if (exif.getExposureTime() == null)
						exif.setExposureTime("not set");
				}
			}
		}

		for (com.drew.metadata.Directory directory : metadata.getDirectories()) {
			for (com.drew.metadata.Tag tag : directory.getTags()) {
				if (tag.getTagName().equals("Model")) {
					exif.setModel(tag.getDescription());
					if (exif.getModel() == null)
						exif.setModel("not set");
				}
			}
		}

		for (com.drew.metadata.Directory directory : metadata.getDirectories()) {
			for (com.drew.metadata.Tag tag : directory.getTags()) {
				if (tag.getTagName().equals("Make")) {
					exif.setManufacturer(tag.getDescription());
					if (exif.getManufacturer() == null)
						exif.setManufacturer("not set");
				}
			}
		}

		for (com.drew.metadata.Directory directory : metadata.getDirectories()) {
			for (com.drew.metadata.Tag tag : directory.getTags()) {
				if (tag.getTagName().equals("Exif Version")) {
					exif.setExifVersion(tag.getDescription());
					if (exif.getExifVersion() == null)
						exif.setExifVersion("not set");
				}
			}
		}

		for (com.drew.metadata.Directory directory : metadata.getDirectories()) {
			for (com.drew.metadata.Tag tag : directory.getTags()) {
				if (tag.getTagName().equals("User Comment")) {
					exif.setDescription(tag.getDescription());
					if (exif.getDescription() == null)
						exif.setDescription("not set");
				}
			}
		}

		log.info(exif.toString());

		return exif;
	}

}
