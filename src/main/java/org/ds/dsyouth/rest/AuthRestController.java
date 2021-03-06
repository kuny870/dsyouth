package org.ds.dsyouth.rest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.ds.dsyouth.common.Constants;
import org.ds.dsyouth.exception.IdDuplicatedException;
import org.ds.dsyouth.exception.IdNoMatchException;
import org.ds.dsyouth.exception.PasswordNoMatchException;
import org.ds.dsyouth.model.Address;
import org.ds.dsyouth.model.Auth;
import org.ds.dsyouth.model.Group;
import org.ds.dsyouth.model.Team;
import org.ds.dsyouth.model.User;
import org.ds.dsyouth.rest.common.ResponseCode;
import org.ds.dsyouth.rest.common.RestResponse;
import org.ds.dsyouth.service.AdminService;
import org.ds.dsyouth.service.AuthService;
import org.ds.dsyouth.utils.StringHelper;
import org.ds.dsyouth.validator.AddressValidator;
import org.ds.dsyouth.validator.UserJoinValidator;
import org.ds.dsyouth.validator.UserLoginValidator;
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
public class AuthRestController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private AdminService adminService;

	@Autowired
	UserLoginValidator userLoginValidator;
	
	@Autowired
	UserJoinValidator userJoinValidator;

	@Autowired
	AddressValidator addressValidator;

	
	@InitBinder("userLogin")
	public void initRegEquipBinder_userLogin(WebDataBinder dataBinder) {
		dataBinder.addValidators(userLoginValidator);
	}
	@InitBinder("userJoin")
	public void initRegEquipBinder_userJoin(WebDataBinder dataBinder) {
		dataBinder.addValidators(userJoinValidator);
	}
	
	@InitBinder("address")
	public void initRegEquipBinder_address(WebDataBinder dataBinder) {
		dataBinder.addValidators(addressValidator);
	}

	
	
	/**
	 * ?????????
	 * @param user
	 * @param userBindingResult
	 * @param request
	 * @param httpResponse
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws IdNoMatchException
	 * @throws PasswordNoMatchException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public RestResponse login(
			@ModelAttribute("userLogin") @Valid User user,
			BindingResult userBindingResult,
			HttpServletRequest request,
			HttpServletResponse httpResponse) throws NoSuchAlgorithmException, UnsupportedEncodingException, IdNoMatchException, PasswordNoMatchException {
		
		RestResponse response = userLoginValidator.bindingError(userBindingResult);
		
		if(response.isSuccess() == false) {
			return response;
		}
		
		try {
			
			authService.getUserCheck(user);
			
			User userTemp = authService.getUserByLoginId(user);
			HttpSession session = request.getSession();
			if(session.getAttribute(Constants.SESSION_USER) != null) {
				session.removeAttribute(Constants.SESSION_USER);
			}
			session.setAttribute(Constants.SESSION_USER, userTemp);
			
		    if(user.getUseCookie().equals("Y")) { 
			  Cookie cookie = new Cookie(Constants.COOKIE_USER, session.getId()); // session.id ?????? cookie??? ??????
			  cookie.setPath("/"); // cookie ??? ?????? ?????? 
			  cookie.setMaxAge(60*60*999999999); // ?????? ???????????? ?????? 
			  httpResponse.addCookie(cookie); // ?????? ?????? response ??? ??????
		  
			  user.setSessionId(session.getId()); 
			  
			  authService.keepLogin(user); 
		    }
			 
			
		} catch (IdNoMatchException e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.ID_NO_MATCH);
		} catch (PasswordNoMatchException e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.PASSWORD_NO_MATCH);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	

	/**
	 * ????????????
	 * @param user
	 * @param userBindingResult
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws IdDuplicatedException
	 */
	@RequestMapping(value = "/join", method = RequestMethod.POST, produces = "application/json")
	public RestResponse join(
			@ModelAttribute("userJoin") @Valid User user,
			BindingResult userBindingResult
			) throws UnsupportedEncodingException, NoSuchAlgorithmException, IdDuplicatedException {

		RestResponse response = userJoinValidator.bindingError(userBindingResult);
		
		if(response.isSuccess() == false) {
			return response;
		}
	
		try {
			authService.registUser(user);
			
		} catch (IdDuplicatedException e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.ID_DUPLICATED);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		response.setData(user.getId());
		
		return response;
	}
	
	
	
	/**
	 * ?????? ???????????? ??????
	 * @param user
	 * @param userBindingResult
	 * @return
	 */
	@RequestMapping(value = "/profile/reset", method = RequestMethod.POST, produces = "application/json")
	public RestResponse reset_info(
			@ModelAttribute("user") @Valid User user,
			HttpServletRequest request,
			BindingResult userBindingResult) {

		RestResponse response = userJoinValidator.bindingError(userBindingResult);
		
		if(response.isSuccess() == false) {
			return response;
		}
		
		try {
			authService.modifyUser(user);
			
			user = authService.getUserByLoginId(user);
			
			HttpSession session = request.getSession();
			if(session.getAttribute(Constants.SESSION_USER) != null) {
				session.removeAttribute(Constants.SESSION_USER);
			}
			session.setAttribute(Constants.SESSION_USER, user);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	/**
	 * ???????????? ??????
	 * @param user
	 * @param currentPw
	 * @param request
	 * @param userBindingResult
	 * @return
	 */
	@RequestMapping(value = "/pw/reset", method = RequestMethod.POST, produces = "application/json")
	public RestResponse reset_pw(
			User user, String currentPw,
			HttpServletRequest request,
			BindingResult userBindingResult) {

//		RestResponse response = userJoinValidator.bindingError(userBindingResult);
		
		RestResponse response = new RestResponse();
		
		try {
			
			User userTemp = new User();
			userTemp.setLoginId(user.getLoginId());
			userTemp.setLoginPw(currentPw);
			
			authService.getUserCheck(userTemp);
			
			authService.modifyUser(user);
			
			user = authService.getUserByLoginId(user);
			
			HttpSession session = request.getSession();
			if(session.getAttribute(Constants.SESSION_USER) != null) {
				session.removeAttribute(Constants.SESSION_USER);
			}
			session.setAttribute(Constants.SESSION_USER, user);
			
		} catch (PasswordNoMatchException e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.PASSWORD_NO_MATCH);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.NO_SUCH_ALGORITHM_EXCEPTION);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNSUPPORTED_ENCODING_EXCEPTION);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	
	/**
	 * ???????????? ?????? - ?????? ???????????? ??????
	 * @param user
	 * @param currentPw
	 * @param request
	 * @param userBindingResult
	 * @return
	 */
	@RequestMapping(value = "/user/pw/reset", method = RequestMethod.POST, produces = "application/json")
	public RestResponse user_pw_reset(User user) {

		RestResponse response = new RestResponse();
		
		try {
			
			authService.modifyUserPwReset(user);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.NO_SUCH_ALGORITHM_EXCEPTION);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNSUPPORTED_ENCODING_EXCEPTION);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	
	/**
	 * ?????? ??????
	 * @param auth_id ????????? ??????
	 * @param arr ????????? ????????? id???
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/authDetail/regist", method = RequestMethod.POST, produces = "application/json")
	public RestResponse authDetail_regist(
			Integer auth_id,
			String[] arr) {

		RestResponse response = new RestResponse();
		
		try {
			
			for (int i = 0; i < arr.length; i++) {
				User user = new User();
				user.setId(StringHelper.parseInt(arr[i]));
				user.setAuthId(auth_id);
				authService.modifyUser(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	/**
	 * ?????? ?????? ??????
	 * @param auth_exec ????????? ??????
	 * @param arr ????????? ????????? id???
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/authExecDetail/regist", method = RequestMethod.POST, produces = "application/json")
	public RestResponse authExecDetail_regist(
			Integer auth_exec,
			String[] arr) {

		RestResponse response = new RestResponse();
		
		try {
			
			for (int i = 0; i < arr.length; i++) {
				User user = new User();
				user.setId(StringHelper.parseInt(arr[i]));
				user.setAuthExec(auth_exec);
				authService.modifyUser(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	
	/**
	 * ?????? ?????????
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/auth/remove", method = RequestMethod.POST, produces = "application/json")
	public RestResponse auth_remove(
			@ModelAttribute Auth auth) {

		RestResponse response = new RestResponse();
		
		try {
			authService.removeAuth(auth);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	/**
	 * ?????? ?????? ?????????
	 * @param auth
	 * @return
	 */
	@RequestMapping(value = "/authExec/remove", method = RequestMethod.POST, produces = "application/json")
	public RestResponse authExec_remove() {

		RestResponse response = new RestResponse();
		
		try {
			authService.removeAuthExec();
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	
	
	/**
	 * ????????? select box ?????? - ?????? ????????? ??? ????????????
	 * @param team
	 * @param request
	 * @param httpResponse
	 * @return
	 */
	@RequestMapping(value = "/select/depart", method = RequestMethod.POST, produces = "application/json")
	public RestResponse select_depart(
			@ModelAttribute("departId") String departId) {
		
		RestResponse response = new RestResponse();
		
		try {
			
			List<Team> teamList = adminService.getTeamByDepart(departId);
			
			response.setData(teamList);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	
	/**
	 * ????????? select box ?????? - ??? ????????? ??? ????????????
	 * @param team
	 * @param request
	 * @param httpResponse
	 * @return
	 */
	@RequestMapping(value = "/select/group", method = RequestMethod.POST, produces = "application/json")
	public RestResponse select_group(Group group) {
		
		RestResponse response = new RestResponse();
		
		try {
			
			List<Group> groupList = adminService.getGroupListByTeam(group);
			
			response.setData(groupList);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	/**
	 * ??????????????? - ?????? ??????
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mypage/address/regist", method = RequestMethod.POST, produces = "application/json")
	public RestResponse address_regist(
			@ModelAttribute("address") @Valid Address address,
			BindingResult addressBindingResult) {
		
		RestResponse response = addressValidator.bindingError(addressBindingResult);
		
		if(response.isSuccess() == false) {
			return response;
		}
		
		try {
			
			authService.registAddress(address);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	/**
	 * ??????????????? - ?????? ??????
	 * @param address
	 * @param request
	 * @param userBindingResult
	 * @return
	 */
	@RequestMapping(value = "/mypage/address/edit", method = RequestMethod.POST, produces = "application/json")
	public RestResponse address_edit(
			@ModelAttribute Address address,
			BindingResult userBindingResult) {

		RestResponse response = userJoinValidator.bindingError(userBindingResult);
		
		if(response.isSuccess() == false) {
			return response;
		}
		
		try {
			
			authService.modifyAddress(address);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	/**
	 * ??????????????? - ?????? ??????
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/mypage/address/remove", method = RequestMethod.POST, produces = "application/json")
	public RestResponse remove(
			@ModelAttribute Address address) {

		RestResponse response = new RestResponse();
		
		try {
			authService.removeAddress(address);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	/**
	 * ?????? ????????????
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json")
	public RestResponse logout(
			@ModelAttribute User user,
			HttpServletRequest request) {

		RestResponse response = new RestResponse();
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		try {
			authService.removeSerssionId(user);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	/**
	 * ?????? ??????
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST, produces = "application/json")
	public RestResponse withdraw(
			@ModelAttribute User user,
			HttpServletRequest request) {

		RestResponse response = new RestResponse();
		
		try {
			boolean result = authService.removeUser(user);
			if(result) {
				HttpSession session = request.getSession();
				session.invalidate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	/**
	 * ?????? ??????
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user/remove", method = RequestMethod.POST, produces = "application/json")
	public RestResponse user_remove(
			@ModelAttribute User user,
			HttpServletRequest request) {

		RestResponse response = new RestResponse();
		
		try {
			authService.removeUser(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
	/**
	 * ?????? ??????
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user/restore", method = RequestMethod.POST, produces = "application/json")
	public RestResponse user_restore(
			@ModelAttribute User user,
			HttpServletRequest request) {

		RestResponse response = new RestResponse();
		
		try {
			authService.restoreUser(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setResCode(ResponseCode.UNKOWN);
		}
		
		return response;
	}
	
	
}