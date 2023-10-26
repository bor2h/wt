## jwt
```json
{
  "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTc5ODcyMzQsImlhdCI6MTY5NzkwMDgzNCwiaXNzIjoid2h5dHJpcCIsImF1ZCI6InVzZXIiLCJzdWIiOiIxIiwibG9naW5JZCI6ImhvbmciLCJyb2xlIjoiR1VFU1QiLCJlbWFpbCI6ImhvbmdAbmF2ZXIuY29tIn0.2IlEGJuimPZ0OFEbWKpsSHnt8-Og_y4433S03Qb_guE",
  "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTc5ODcyMzQsImlhdCI6MTY5NzkwMDgzNCwiaXNzIjoid2h5dHJpcCIsImF1ZCI6InVzZXIiLCJzdWIiOiIxIiwibG9naW5JZCI6ImhvbmciLCJyb2xlIjoiR1VFU1QiLCJlbWFpbCI6ImhvbmdAbmF2ZXIuY29tIn0.2IlEGJuimPZ0OFEbWKpsSHnt8-Og_y4433S03Qb_guE"
}
```

## [JWT 디코딩 Site](https://jwt.io/)


### 디코딩한 jwt
#### HEADER:ALGORITHM & TOKEN TYPE
```json
{
  "typ": "JWT",
  "alg": "HS256"
}
```

#### PAYLOAD:DATA
```json
{
  "exp": 1697987234,
  "iat": 1697900834,
  "iss": "whytrip",
  "aud": "user",
  "sub": "1",
  "loginId": "hong",
  "role": "GUEST",
  "email": "hong@naver.com"
}
```

# jwt 설정
```yaml
# application.yml
jwt:
  secret: 1e47d80ac0698f4cf303c6e7ba9a64a07767a21cb3674336489a7d96dee4762ee11e6827a04fa4c172174e88063f085f1d3c9370ead4f3aab44d30b31ea7bd05
  accessToken:
    # starter 테스트 용으로 24 시간으로 해놓음 추후 변경예정
    # 1000 * 60 * 60* 24 = 86400000 (24 시간)
    expiration: 86400000 # 액세스 토큰 만료 시간 (초 단위)
  refreshToken:
    # 1000 * 60 * 60* 24 = 86400000
    expiration: 86400000 # 리프레시 토큰 만료 시간 (초 단위)
  issuer: whytrip  # JWT 발급자(issuer)
  audience: user   # JWT 대상(audience)
```
- accessToken 의 유효시간을 jwt.accessToken.expiration 에서 설정할 수 있습니다.
- refreshToken 의 유효시간을 jwt.refreshToken.expiration 에서 설정할 수 있습니다.