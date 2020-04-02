/**
 * 格式校验
 * @param path
 * @returns {boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

export function validUsername(str) {
  const valid_map = ['admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}
//校验身份证
export function isIdCard(str) {
  if (!str) {
    return false;
  }
  return /(^\d{17}[xX0-9]$)|(^\d{14}[xX0-9]$)/i.test(str);
}

//校验护照
export function isPassport(str) {
  if (!str) {
    return false;
  }
  return /^[a-zA-Z0-9]{4,}$/i.test(str);
}

//校验手机号
export function isMobile(str) {
  if (!str) {
    return false;
  }
  return /^1\d{10}$/i.test(str);
}

//校验邮箱
export function isEmail(str) {
  if (!str) {
    return false;
  }
  return /^\w+@[a-z0-9]+\.[a-z]+$/i.test(str);
}

//校验邮政编码
export function isPostcode(str) {
  if (!str) {
    return false;
  }
  return /^[0-9]{6}$/.test(str);
}

//判断受否有汉字
export function hasChinese(str) {
  if (!str) {
    return false;
  }
  return /.*[\u4e00-\u9fa5]+.*$/.test(str);
}

//只包含字母和数字
export function onlyZmAndSz(str) {
  return  /^[A-Za-z0-9]+$/.test(str);
}

//只包含字母和数字和空格
export function onlyZmAndSzAndSpace(str) {
  return  /^[A-Za-z0-9 ]+$/.test(str);
}


//只包含数字
export function onlySz(str) {
  return  /^[0-9]+$/.test(str);
}
