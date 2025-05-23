package com.efub.agodaclone.user.service;

import com.efub.agodaclone.user.dto.KakaoUserResponseDto;
import com.efub.agodaclone.user.dto.KakaoTokenResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public String getAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KakaoTokenResponseDto> response = restTemplate.postForEntity(tokenUrl, request, KakaoTokenResponseDto.class);

        return response.getBody().getAccessToken();
    }

    //카카오에서 user 정보 받아옴
    public KakaoUserResponseDto getUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KakaoUserResponseDto> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, KakaoUserResponseDto.class);

        return response.getBody();
    }

    public void unlinkKakao(String kakaoAccessToken) {
        if (kakaoAccessToken == null) {
            System.out.println("kakaoAccessToken is null");
            return;
        }

        String url = "https://kapi.kakao.com/v1/user/unlink";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(kakaoAccessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("카카오 unlink 성공: " + response.getBody());
        } catch (HttpClientErrorException e) {
            System.out.println("unlink 실패: " + e.getStatusCode() + " / " + e.getResponseBodyAsString());
        }
    }

}
