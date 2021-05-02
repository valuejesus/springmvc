package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) throw new UsernameNotFoundException("");

        UserModel userModel = userRepository.selectByUser(username);

        // ユーザが存在しない場合
        if (userModel == null) throw new UsernameNotFoundException("");
        // アカウントの有効期限切れ、アカウントのロック、パスワードの有効期限切れ、ユーザの無効を判定
        if (!userModel.isAccountNonExpired() || !userModel.isAccountNonLocked() ||
                !userModel.isCredentialsNonExpired() || !userModel.isEnabled())
            throw new UsernameNotFoundException("");
        return userModel;
    }

}
