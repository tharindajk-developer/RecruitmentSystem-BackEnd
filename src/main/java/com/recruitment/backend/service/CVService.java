/*
 * Recruitment System 2022
 */

package com.recruitment.backend.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.recruitment.backend.entity.CV;
import com.recruitment.backend.entity.Experience;
import com.recruitment.backend.entity.Qualification;
import com.recruitment.backend.entity.Skill;
import com.recruitment.backend.entity.User;
import com.recruitment.backend.enums.RolesEnum;
import com.recruitment.backend.repository.CVRepository;
import com.recruitment.backend.repository.ExperienceRepository;
import com.recruitment.backend.repository.QualificationsRepository;
import com.recruitment.backend.repository.SkillRepository;
import com.recruitment.backend.repository.UserRepository;

/*
 *tharinda.jayamal@gmail.com
 */
@Service
public class CVService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${cv.template}")
	private String cvTemplate;

	@Autowired
	private CVRepository cvRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExperienceRepository experienceRepository;
	
	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private QualificationsRepository qualificationsRepository;
	
	@Autowired
	private UserService userService;

	public String addUpdateCV(CV cv, Long userId) {

		User user = null;
		String status = "Failed to store CV";
		if (userRepository.existsById(userId)) {
			user = userRepository.findById(userId).get();
		}

		if (user != null && user.getRole() != null
				&& user.getRole().getName().equalsIgnoreCase(RolesEnum.JOBSEEKER.toString())) {
			user.setCv(cv);
			
			cvRepository.save(cv);
			
			Set<Experience> experiences = new HashSet<>();			
			for(Experience e : cv.getExperiences()){
				e.setCv(cv);
				experiences.add(e);
			}		
			experienceRepository.saveAll(experiences);
			//cv.setExperiences(experiences);
			
			Set<Skill> skills = new HashSet<>();			
			for(Skill s : cv.getSkills()){
				s.setCv(cv);
				skills.add(s);
			}
			skillRepository.saveAll(skills);
			//cv.setSkills(skills);
			
			Set<Qualification> qualifications = new HashSet<>();			
			for(Qualification q : cv.getQualifications()){
				q.setCv(cv);
				qualifications.add(q);
			}			
			//cv.setQualifications(qualifications);
			qualificationsRepository.saveAll(qualifications);
			
	
			status = "Successfully stored the CV ";
		}
		return status;

	}

	public CV getCVByUserId(Long userId) {

		log.debug("Fetching CV for user:" + userId);
		if (userRepository.findById(userId).isPresent()) {
			
			User u = userRepository.findById(userId).get();
			return userService.findByUserName(u.getUserName()).getCv();
		} else {
			return null;
		}
	}

	public void deleteCV(Long userId) {

		log.debug("Deleting CV by user Id: " + userId);

		User user = null;
		if (userRepository.existsById(userId)) {
			user = userRepository.findById(userId).get();

			if (user.getCv() != null && user.getCv().getId() != null) {
				user.setCv(null);
				userRepository.save(user);
			}
		}
	}

	public byte[] generatedPDF(Long userId, String outputhFilePath)
			throws DocumentException, IOException {

		User user = userRepository.findById(userId).get();

		if (user.getRole() != null
				&& user.getRole().getName().equalsIgnoreCase(RolesEnum.JOBSEEKER.toString())) {
			log.info("Preparing CV report for " + userId);
		}
		OutputStream fos = new FileOutputStream(new File(outputhFilePath));

		PdfReader pdfReader = new PdfReader(cvTemplate);
		PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);

		if (user.getRole() != null
				&& user.getRole().getName().equalsIgnoreCase(RolesEnum.JOBSEEKER.toString())) {
			for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
				if (i == 1) {
					PdfContentByte pdfContentByte = pdfStamper
							.getOverContent(i);

					pdfContentByte.beginText();
					pdfContentByte.setFontAndSize(BaseFont.createFont(
							BaseFont.COURIER, BaseFont.CP1257,
							BaseFont.EMBEDDED), 10);

					pdfContentByte.setTextMatrix(102, 622);
					pdfContentByte.showText(user.getFirstName() + " "
							+ user.getLastName());

					pdfContentByte.setTextMatrix(102, 601);
					pdfContentByte.showText(new SimpleDateFormat(
							"dd-MM-yyyy HH:mm").format(new Date()));

					pdfContentByte.setTextMatrix(102, 580);
					pdfContentByte.showText(user.getRole().getName());

					int x = 102;
					int y = 557;

					/*
					 * if (user.getCorporateCustomer().getAccount()
					 * .getTransactions() != null) {
					 * 
					 * pdfContentByte.setTextMatrix(x, y);
					 * pdfContentByte.setTextMatrix(x, y);
					 * pdfContentByte.showText("Reference" + "     " + "Amount"
					 * + "   " + "From" + "            " + "To" +
					 * "             " + "Date" + "               " + "Type"); y
					 * -= 13;
					 * 
					 * for (Transaction transaction : user
					 * .getCorporateCustomer().getAccount() .getTransactions())
					 * { if (StringUtils.isEmpty(transaction.getFromAcc())) {
					 * transaction.setFromAcc("----Bank----"); } if
					 * (StringUtils.isEmpty(transaction.getToAcc())) {
					 * transaction.setToAcc("----Bank----"); }
					 * pdfContentByte.setTextMatrix(x, y);
					 * pdfContentByte.showText(transaction .getTransactionRef()
					 * + "   " + transaction.getTransactionAmount() .setScale(2,
					 * RoundingMode.CEILING) + "   " + transaction.getFromAcc()
					 * + "   " + transaction.getToAcc() + "   " + new
					 * SimpleDateFormat("dd-MM-yyyy HH:mm") .format(transaction
					 * .getTransactionDate()) + "   " +
					 * transaction.getTransactionType()); y -= 13; } }
					 */

					pdfContentByte.endText();
				}

			}
		}

		pdfStamper.close();
		pdfReader.close();

		return readFile(outputhFilePath);
	}

	private byte[] readFile(String path) throws IOException {
		InputStream is = new FileInputStream(path);
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[1024];
		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		return buffer.toByteArray();
	}

}
