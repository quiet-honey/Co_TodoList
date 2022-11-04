import { API_BASE_URL } from "../app-config";

export const call = (api, method, request) => {
  let options = {
    headers: new Headers({
      "Content-Type": "application/json",
    }),
    url: API_BASE_URL + api,
    method: method,
  };
  if (request) {
    // 매개변수로 request가 들어오면 request body에 넣어 보낸다는 뜻
    options.body = JSON.stringify(request);
  }
  return fetch(options.url, options).then((response) =>
    response.json().then((json) => {
      if (!response.ok) {
        // response.ok가 true이면 정상적인 리스폰스를 받은것, 아니면 에러 리스폰스를 받은것.
        return Promise.reject(json);
      }
      return json;
    })
  );
};
