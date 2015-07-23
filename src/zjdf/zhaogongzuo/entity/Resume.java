package zjdf.zhaogongzuo.entity;


import java.util.List;

/**
 * 简历
 * 
 * @author Administrator
 * @modify by Eilin.Yang
 */
public class Resume {

	/**基本信息*/
	public ResumeInformation mBaseInfo;
	
	/**求职意向*/
	public ResumeJobIntention mJobIntention;
	
	/**教育经历*/
	public List<ResumeEducation> mEducations;
	
	/**工作经验*/
	public List<ResumeWorks> mWorks;
	
	/**语言能力*/
	public List<ResumeLanguage> mLanguage;
	
	/**技能与特长*/
	public List<ResumeSkill> mSkill;
	
	/**培训经历*/
	public List<ResumeTrains> mTrains;
	
	/**证书*/
	public List<ResumeCertificate> mCertificates;
	
	/**其他*/
	public List<ResumeOther> mOthers;
	
	/**附件*/
	public List<ResumeFile> mFiles;
}
