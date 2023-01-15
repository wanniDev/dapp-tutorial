// SPDX-License-Identifier: MIT
pragma solidity ^0.8.17;

contract Shop {
  mapping (address=>uint16) myApple; // 몇 개의 사과가 남았는지 address 에 uint16 맵핑

  function buyApple() payable external { // 외부에서 트랜잭션을 실행하기 위해 payable external 사용
    myApple[msg.sender]++; // sender 의 MyApple 증가
  }

  function getMyApple() view external returns(uint16) { // 상태 변경 없이 바로 값을 반환하므로 view 선언. 그리고 외부에서 호출 가능하도록 external 선언
    return myApple[msg.sender];
  }

  function sellMyApple(uint _applePrice) payable external { // payable: 트랜잭션 실행 가능하게 하는 예약어, external: 외부에 실행 가능하게 하는 예약어
    uint refund = (myApple[msg.sender] * _applePrice); // _applePrice 를 서버에서 받아와서 현재 계정이 보유한 개수와 곱하여 총 가격 산출
    myApple[msg.sender] = 0;                           // sender 의 myApple 보유량을 0으로 초기화
    // https://stackoverflow.com/questions/67341914/error-send-and-transfer-are-only-available-for-objects-of-type-address-payable
    payable(msg.sender).transfer(refund);                      // 계산한 만큼 액수 전송
  }
}
