package com.hopin.HopIn.entities;

import java.io.UnsupportedEncodingException;

import com.hopin.HopIn.dtos.DocumentRequestDTO;
import com.hopin.HopIn.enums.DocumentOperationType;
import com.hopin.HopIn.enums.RequestStatus;
import com.hopin.HopIn.enums.RequestType;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "driver_account_update_document_requests")
public class DriverAccountUpdateDocumentRequest extends DriverAccountUpdateRequest{

	private int documentId;
	
	@NotEmpty
	@Pattern(regexp = "^([a-zA-Zčćđžš ]*)$")
	private String name;
	
	@Lob
	private byte[] documentImage;
	
	@NotNull
	private DocumentOperationType documentOperationType;
	
	public DriverAccountUpdateDocumentRequest() {
		
	}

	public DriverAccountUpdateDocumentRequest(int id, RequestStatus status, String reason, Driver driver, Administrator admin, int documentId, 
			String name, byte[] documentImage, DocumentOperationType type) {
		super(id, status, reason, driver, admin, RequestType.DOCUMENT);
		this.documentId = documentId;
		this.name = name;
		this.documentImage = documentImage;
		this.documentOperationType = type;
	}
	
	public DriverAccountUpdateDocumentRequest(DocumentRequestDTO dto, Driver driver) {
		super(RequestStatus.PENDING, "", driver, null, RequestType.DOCUMENT);
		this.name = dto.getName();
		this.documentImage = dto.getDocumentImage().getBytes();
		this.documentOperationType = dto.getDocumentOperationType();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocumentImage() {
		String s;
		try {
			s = "data:image/jpeg;base64, ";
			s = s + new String(this.documentImage, "UTF-8");
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setDocumentImage(String documentImage) {
		String[] picture = documentImage.split(",");
		if (picture.length >= 2) {
			byte[] decoded;
			try {
				decoded = picture[1].getBytes("UTF-8");
				this.documentImage = decoded;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	public DocumentOperationType getDocumentOperationType() {
		return documentOperationType;
	}

	public void setDocumentOperationType(DocumentOperationType type) {
		this.documentOperationType = type;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
}
