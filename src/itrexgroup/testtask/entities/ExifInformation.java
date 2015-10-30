package itrexgroup.testtask.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ExifInformation implements Serializable{

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Integer id;
	private String manufacturer;
	private String model;
	private Date dateAndTime;
	private String Compression;
	private String exposureTime;
	private String exifVersion;
	@Column(columnDefinition="LONGTEXT")
	private String exifDescription;
	private double latitude;
 	double longitude;
 	private String Description;
		 	
	public ExifInformation() {
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Date getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	public String getCompression() {
		return Compression;
	}
	public void setCompression(String compression) {
		Compression = compression;
	}
	public String getExposureTime() {
		return exposureTime;
	}
	public void setExposureTime(String string) {
		this.exposureTime = string;
	}
	public String getExifVersion() {
		return exifVersion;
	}
	public void setExifVersion(String exifVersion) {
		this.exifVersion = exifVersion;
	}
	public String getExifDescription() {
		return exifDescription;
	}
	public void setExifDescription(String exifDescription) {
		this.exifDescription = exifDescription;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double d) {
		this.latitude = d;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double d) {
		this.longitude = d;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}

	@Override
	public String toString() {
		return "ExifInformation [id=" + id + ", manufacturer=" + manufacturer
				+ ", model=" + model + ", dateAndTime=" + dateAndTime
				+ ", Compression=" + Compression + ", exposureTime="
				+ exposureTime + ", exifVersion=" + exifVersion
				+ ", exifDescription=" + exifDescription + ", latitude="
				+ latitude + ", longitude=" + longitude + ", Description="
				+ Description + "]";
	}

	
	
}
