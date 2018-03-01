package com.leibangzhu.starters.auth.interceptor;

import com.leibangzhu.starters.auth.Principal;
import com.leibangzhu.starters.auth.WebContext;
import com.leibangzhu.starters.auth.annotation.RequireAllPermissions;
import com.leibangzhu.starters.auth.annotation.RequireAllRoles;
import com.leibangzhu.starters.auth.annotation.RequireOnePermission;
import com.leibangzhu.starters.auth.annotation.RequireOneRole;
import com.leibangzhu.starters.common.Response;
import com.leibangzhu.starters.common.Result;
import com.leibangzhu.starters.common.util.JsonUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 检查用户是否有权限
public class CheckPermissionInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        HandlerMethod method = (HandlerMethod) o;
        Result result;

        result = check4RequireAllRoles(method);
        if (!result.isSuccess()){
            Response error = Response.newBuilder().error(result.getErrorMsg()).build();
            httpServletResponse.getOutputStream().write(JsonUtil.serialize(error).getBytes());
            return false;
        }

        result = check4RequireOneRole(method);
        if (!result.isSuccess()){
            Response error = Response.newBuilder().error(result.getErrorMsg()).build();
            httpServletResponse.getOutputStream().write(JsonUtil.serialize(error).getBytes());
            return false;
        }

        result = check4RequireAllPermissions(method);
        if (!result.isSuccess()){
            Response error = Response.newBuilder().error(result.getErrorMsg()).build();
            httpServletResponse.getOutputStream().write(JsonUtil.serialize(error).getBytes());
            return false;
        }

        result = check4RequireOnePermission(method);
        if (!result.isSuccess()){
            Response error = Response.newBuilder().error(result.getErrorMsg()).build();
            httpServletResponse.getOutputStream().write(JsonUtil.serialize(error).getBytes());
            return false;
        }

        return true;
    }

    private Result check4RequireAllPermissions(HandlerMethod method){
        RequireAllPermissions requireAllPermissions = method.getMethodAnnotation(RequireAllPermissions.class);
        if (null == requireAllPermissions){
            return Result.success();
        }
        String[] permissions = requireAllPermissions.value();
        if (permissions.length == 0){
            return Result.success();
        }

        // 获取当前登录用户
        Principal user = WebContext.currentUser();
        for (String permission : permissions){
            if (!user.getPermissions().contains(permission)){
                return Result.fail("用户必须要有权限:" + permission);
            }
        }

        return Result.success();
    }

    private Result check4RequireOnePermission(HandlerMethod method){
        RequireOnePermission requireOnePermission = method.getMethodAnnotation(RequireOnePermission.class);
        if (null == requireOnePermission){
            return Result.success();
        }

        String[] permissions = requireOnePermission.value();
        if (permissions.length == 0){
            return Result.success();
        }

        // 获取当前登录用户
        Principal user = WebContext.currentUser();
        for (String permission : permissions){
            if (user.getPermissions().contains(permission)){
                return Result.success();
            }
        }

        return Result.fail("用户必须要有其中一个权限:" + String.join(",",permissions));
    }

    private Result check4RequireAllRoles(HandlerMethod method){
        RequireAllRoles requireAllRoles = method.getMethodAnnotation(RequireAllRoles.class);
        if (null == requireAllRoles){
            return Result.success();
        }
        String[] roles = requireAllRoles.value();
        if (roles.length == 0){
            return Result.success();
        }

        // 获取当前登录用户
        Principal user = WebContext.currentUser();
        for (String role : roles){
            if (!user.getRoles().contains(role)){
                return Result.fail("用户必须要有角色:" + role);
            }
        }

        return Result.success();
    }

    private Result check4RequireOneRole(HandlerMethod method){
        RequireOneRole requireOneRole = method.getMethodAnnotation(RequireOneRole.class);
        if (null == requireOneRole){
            return Result.success();
        }

        String[] roles = requireOneRole.value();
        if (roles.length == 0){
            return Result.success();
        }

        // 获取当前登录用户
        Principal user = WebContext.currentUser();
        for (String role : roles){
            if (user.getRoles().contains(role)){
                return Result.success();
            }
        }

        return Result.fail("用户必须要有其中一个角色:" + String.join(",",roles));
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
