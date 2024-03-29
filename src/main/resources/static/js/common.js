/* 공통 사용되는 JS */

/**
 * 인증번호 전송
 * @param celno
 * @param callback
 */
function sendAuthCodeSMS(celno, callback){
  if(celno === null || celno === '' || celno === undefined){
    alert('연락처를 입력해주세요.');
  } else[
    // 서버에 axios post요청으로 인증번호 발송
    axios.post(`/user/sendAuthSMS`, {celno: celno}) // url, parameter, config
        .then(res => { // 응답 받는경우
          if(res.data.length === 6){
            alert('인증번호가 발송되었습니다.');
            callback(res.data);
          } else{
            alert('[SMS 발송에러]');
          }
        })
        .catch(err => { // 에러처리
          console.log(err);
          alert('ERROR');
        })
  ]

}

/**
 * 하위코드 얻어오기
 * return List<CodeVO>
 */
function getLowCodeCommon(code, callback){
  axios.get(`/common/lowlevelcode/` + code // 요청 urL, get방식
      ,{withCredentials: false})
      .then(res => { // promise 형식, 응답받음// res객체애 응답 데이터 담아옴
        callback(res);
      })
      .catch(err => { // 에러 처리
        console.log(err);
        alert('ERROR');
      })
}