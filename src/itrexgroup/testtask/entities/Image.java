package itrexgroup.testtask.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import net.coobird.thumbnailator.Thumbnails;

@Entity
public class Image implements Serializable{

	private Integer id;
	private String name;
	private Date uploadDate;
	private ExifInformation exif;
	@Transient
	private String imagePath;
	@Transient
	private String  thumbPath;
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Image(String name, Date uploadDate) {
		this.name = name;
		this.uploadDate = uploadDate;
	}
	public Image() {
		}

	@Override
	public String toString() {
		return "ImageEntity [uploadDate=" + uploadDate + "]";
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	public ExifInformation getExif() {
		return exif;
	}
	public void setExif(ExifInformation exif) {
		this.exif = exif;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getThumbPath() {
		return thumbPath;
	}
	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
	
	
}
