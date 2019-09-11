package com.joy.xxfy.informationallyt.module.system.service;

import com.joy.xxfy.informationallyt.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationallyt.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationallyt.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationallyt.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationallyt.module.system.domain.repository.UserRepository;
import com.joy.xxfy.informationallyt.module.system.web.req.UserAddReq;
import com.joy.xxfy.informationallyt.module.system.web.req.UserUpdateReq;
import com.joy.xxfy.informationallyt.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationallyt.publish.result.JoyResult;
import com.joy.xxfy.informationallyt.publish.result.Notice;
import com.joy.xxfy.informationallyt.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationallyt.publish.utils.JpaPagerUtil;
import com.joy.xxfy.informationallyt.publish.utils.MD5Util;
import com.joy.xxfy.informationallyt.publish.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public JoyResult add(UserAddReq req) {
        /**
         * 检测密码是否一致
         */
        if(!StringUtil.equals(req.getPassword(),req.getAffirmPassword())){
            return JoyResult.buildFailedResult(Notice.PASSWORD_AFFIRM_ERROR);
        }
        /**
         * 校验登录名
         */
        UserEntity checkUser = userRepository.findAllByLoginName(req.getLoginName());;
        if(null != checkUser){
            return JoyResult.buildFailedResult(Notice.LOGIN_NAME_ALREADY_EXIST);
        }
        // 获取所属部门
        DepartmentEntity department = departmentRepository.findAllById(req.getDepartmentId());
        if(department == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }

        UserEntity user = new UserEntity();
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, user);
        user.setDepartment(department);
        // 密码
        user.setPassword(MD5Util.encode(user.getPassword()));
        // 保存数据
        UserEntity save = userRepository.save(user);
        return JoyResult.buildSuccessResultWithData(save);
    }

    /**
     * get
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(userRepository.findById(id));
    }


    /**
     * update
     */
    public JoyResult update(UserUpdateReq req) {
        UserEntity info = userRepository.findAllById(req.getId());
        /**
         * 检测密码是否一致
         */
        if(!StringUtil.equals(req.getPassword(),req.getAffirmPassword())){
            return JoyResult.buildFailedResult(Notice.PASSWORD_AFFIRM_ERROR);
        }
        /**
         * 校验登录名
         */
        UserEntity checkUser = userRepository.findAllByLoginName(req.getLoginName());;
        if(null != checkUser && !info.getId().equals(checkUser.getId())){
            return JoyResult.buildFailedResult(Notice.LOGIN_NAME_ALREADY_EXIST);
        }
        // 获取所属部门
        DepartmentEntity department = departmentRepository.findAllById(req.getDepartmentId());
        if(department == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }

        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        info.setDepartment(department);
        // 密码
        info.setPassword(MD5Util.encode(info.getPassword()));
        // 保存数据
        UserEntity save = userRepository.save(info);
        return JoyResult.buildSuccessResultWithData(save);
    }



    public JoyResult delete(Long id) {
        UserEntity dbUser = userRepository.findAllById(id);
        if(dbUser == null){
            return JoyResult.buildFailedResult(Notice.USER_NOT_EXIST);
        }
        try{
            userRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return JoyResult.buildFailedResult(Notice.DATA_IN_USED_CANT_BE_DELETE);
        }
        return JoyResult.buildSuccessResultWithData(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(BasePageReq req ) {
        return JoyResult.buildSuccessResultWithData(userRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(BasePageReq req) {
        return JoyResult.buildSuccessResultWithData(userRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<UserEntity> getPredicates(BasePageReq req ){
        return (Specification<UserEntity>) (root, query, builder) -> {
            List<Predicate> predicates1 = new ArrayList<>();

            List<Predicate> predicates2 = new ArrayList<>();
            // loginName
            if(req.getSearch() != null){
                predicates2.add(builder.like(root.get("loginName"), "%" + req.getSearch() + "%"));
                predicates2.add(builder.like(root.get("username"), "%" + req.getSearch() + "%"));
            }
            List<Predicate> result = new ArrayList<>();
            Predicate or = builder.or(predicates1.toArray(new Predicate[predicates1.size()]));
            Predicate and = builder.and(predicates2.toArray(new Predicate[predicates2.size()]));
            result.add(or);
            result.add(and);
            return builder.or(result.toArray(new Predicate[result.size()]));
        };
    }


}
