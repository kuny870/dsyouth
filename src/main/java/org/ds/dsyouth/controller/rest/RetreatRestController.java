package org.ds.dsyouth.controller.rest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.ds.dsyouth.controller.rest.common.ResponseCode;
import org.ds.dsyouth.controller.rest.common.RestResponse;
import org.ds.dsyouth.exception.CreateRetreatTableException;
import org.ds.dsyouth.exception.IdDuplicatedException;
import org.ds.dsyouth.model.Family;
import org.ds.dsyouth.model.FamilyMember;
import org.ds.dsyouth.model.Retreat;
import org.ds.dsyouth.service.RetreatService;
import org.ds.dsyouth.validator.RetreatValidator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rest")
public class RetreatRestController {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RetreatRestController.class);
	
	@Autowired
	private RetreatService retreatService;

	@Autowired
	RetreatValidator retreatValidator;
	
	@InitBinder("retreat")
	public void initRegEquipBinder(WebDataBinder dataBinder) {
		dataBinder.addValidators(retreatValidator);
	}


	
	/**
	 * 수련회 등록
	 * @param retreat
	 * @param retreatBindingResult
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws IdDuplicatedException
	 */
	@RequestMapping(value = "/retreat/regist", method = RequestMethod.POST, produces = "application/json")
	public RestResponse retreat_regist(
			@ModelAttribute("retreat") @Valid Retreat retreat,
			BindingResult retreatBindingResult,
			HttpServletRequest request
			) throws UnsupportedEncodingException, NoSuchAlgorithmException, IdDuplicatedException {

		RestResponse response = retreatValidator.bindingError(retreatBindingResult);
		
		if(response.isSuccess() == false) {
			return response;
		}
	
		try {
			retreatService.registRetreat(retreat);
			
		} catch (CreateRetreatTableException e) {
			response.setSuccess(false);
			response.setResCode(ResponseCode.RETREATTABLE_DUPLICATED);
			logger.error("retreat_regist error : " + e.getLocalizedMessage());
		} catch (Exception e) {
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
			logger.error("retreat_regist error : " + e.getLocalizedMessage());
		}
		
		return response;
	}
	
	
	
	/**
	 * 가족 등록
	 * @param family
	 * @return
	 */
	@RequestMapping(value = "/family/regist", method = RequestMethod.POST, produces = "application/json")
	public RestResponse family_regist(
			@ModelAttribute Family family) {

		RestResponse response = new RestResponse();
		
		try {
			retreatService.registFamily(family);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
			logger.error("retreat_regist error : " + e.getLocalizedMessage());
		}
		
		return response;
	}
	

	/**
	 * 가족원 등록
	 * @param familyMember
	 * @return
	 */
	@RequestMapping(value = "/familyMember/regist", method = RequestMethod.POST, produces = "application/json")
	public RestResponse familyMember_regist(
			String fId,
			String[] chksArr,
			String[] departIdArr,
			String[] teamIdArr,
			String[] groupIdArr,
			Integer[] fmIdArr,
			HttpServletRequest request) {

		RestResponse response = new RestResponse();
		
		try {
			
			Family family = new Family();
			
			family = retreatService.getFamily(fId);
			Retreat retreat = retreatService.getRetreat(family.getRetreatId());
			
			FamilyMember fm = null;
			
			for (int i = 0; i < chksArr.length; i++) {
				fm = new FamilyMember();
				if(fmIdArr[i] != 0) {
					fm.setId(fmIdArr[i]);
				}
				fm.setDepartId(departIdArr[i]);
				fm.setTeamId(teamIdArr[i]);
				fm.setGroupId(groupIdArr[i]);
				fm.setMemberId(chksArr[i]);
				fm.setFamilyId(fId);
				fm.setRetreatId(family.getRetreatId());
				fm.setRetreat(retreat);
				retreatService.modifyFamilyMember(fm);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	/**
	 * 가족원 groupGrade 수정
	 * @param fm
	 * @return
	 */
	@RequestMapping(value = "/familyMember/edit", method = RequestMethod.POST, produces = "application/json")
	public RestResponse familyMember_edit(
			@ModelAttribute FamilyMember fm) {

		RestResponse response = new RestResponse();
		
		try {
			Retreat retreat = retreatService.getRetreat(fm.getRetreatId());
			fm.setRetreat(retreat);
			
			retreatService.modifyFamilyMember(fm);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
			logger.error("familyMember_edit error : " + e.getLocalizedMessage());
		}
		
		return response;
	}
	
	
	/**
	 * 수련회 삭제
	 * @param retreat
	 * @return
	 */
	@RequestMapping(value = "/retreat/remove", method = RequestMethod.POST, produces = "application/json")
	public RestResponse retreat_remove(
			@ModelAttribute Retreat retreat) {

		RestResponse response = new RestResponse();
		
		try {
			retreatService.removeRetreat(retreat);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
			logger.error("retreat_remove error : " + e.getLocalizedMessage());
		}
		
		return response;
	}
	
	
	/**
	 * 가족 삭제
	 * @param family
	 * @return
	 */
	@RequestMapping(value = "/family/remove", method = RequestMethod.POST, produces = "application/json")
	public RestResponse family_remove(
			@ModelAttribute Family family) {

		RestResponse response = new RestResponse();
		
		try {
			family = retreatService.getFamily(family.getId().toString());
			Retreat retreat = retreatService.getRetreat(family.getRetreatId());
			family.setRetreat(retreat);
			
			retreatService.removeFamily(family);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
}