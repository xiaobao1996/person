//机构数组转树型结构
export function arrayToTreeByTopId(topId, dataList) {
  if (!dataList || !dataList.length || dataList.length < 1) {
    return [];
  }
  let result = [];
  for (let i = 0; i < dataList.length; i ++) {
    if (dataList[i].parentId === topId) {
      result.push(dataList[i]);
      dataList.splice(i, 1);
      i --;
    }
  }

  if (result.length > 0 && dataList.length > 0) {
    for (let i = 0; i < result.length; i ++) {
      result[i].children = arrayToTreeByParent(result[i].id, dataList);
    }
  }

  return result;
}


//机构数组转树型结构
export function arrayToTree(dataList) {
  if (!dataList || !dataList.length || dataList.length < 1) {
    return [];
  }
  let result = [];
  for (let i = 0; i < dataList.length; i ++) {
    if (dataList[i].parentId === '0') {
      result.push(dataList[i]);
      dataList.splice(i, 1);
      i --;
    }
  }

  if (result.length > 0 && dataList.length > 0) {
    for (let i = 0; i < result.length; i ++) {
      result[i].children = arrayToTreeByParent(result[i].id, dataList);
    }
  }

  return result;
}



//机构数组转树型结构
export function arrayToTreeByParent(parentId, officeList) {
  if (!officeList || !officeList.length || officeList.length < 1) {
    return [];
  }
  let result = [];
  for (let i = 0; i < officeList.length; i ++) {
    if (officeList[i].parentId === parentId) {
      result.push(officeList[i]);
      officeList.splice(i, 1);
      i --;
    }

  }
  if (officeList.length > 0) {
    for (let i = 0; i < result.length; i ++) {
      result[i].children = officeArrayToTree(result[i].id, officeList) || [];
    }
  }
  return result;
}


//机构数组转树型结构
export function officeArrayToTree(parentId, officeList) {
  if (!officeList || !officeList.length || officeList.length < 1) {
    return [];
  }
  let result = [];
  for (let i = 0; i < officeList.length; i ++) {
    if (officeList[i].parentId === parentId) {
      result.push(officeList[i]);
      officeList.splice(i, 1);
      i --;
    }

  }
  if (officeList.length > 0) {
    for (let i = 0; i < result.length; i ++) {
      result[i].children = officeArrayToTree(result[i].id, officeList) || [];
    }
  }
  return result;
}

//机构数组转树型结构 按照机构类型分类
export function officeArrayToTreeWithOfficeType(parentId, officeList) {
  if (!officeList || !officeList.length || officeList.length < 1) {
    return [];
  }
  let result = [];
  for (let i = 0; i < officeList.length; i ++) {
    if (officeList[i].parentId === parentId) {
      result.push(officeList[i]);
      officeList.splice(i, 1);
      i --;
    }

  }
  if (officeList.length > 0) {
    for (let i = 0; i < result.length; i ++) {
      result[i].children = officeArrayToTree(result[i].id, officeList) || [];
    }
  }

  let initOfficeType = function(result) {
    result.forEach((rTmp) => {
      //是学校，进行分组
      if (rTmp.officeType === 0 && isArrayHas(rTmp.children)) {
        let has1 = false, has2 = false, has3 = false, has0 = false;
        rTmp.children.forEach((cTmp) => {
          switch (cTmp.officeType) {
            case 0: has0 = true;break;
            case 1: has1 = true;break;
            case 2: has2 = true;break;
            case 3: has3 = true;break;
          }
        });
        if (has1 || has2 || has3) {
          let children = rTmp.children;
          let newChildren = [];
          let newSchoolChildren = [];
          if (has1) {
            let label1 =  {officeName : "行政部门", children : [], officeType : 1, hiddenId : rTmp.id, label : true};
            children.forEach((c) => {
              if (c.officeType === 1) {
                label1.children.push(c);
              }
            })
            newChildren.push(label1);
          }
          if (has2) {
            let label1 =  {officeName : "在校班级", children : [], officeType : 2, hiddenId : rTmp.id, label : true};
            children.forEach((c) => {
              if (c.officeType === 2) {
                label1.children.push(c);
              }
            })
            newChildren.push(label1);
          }
          if (has3) {
            let label1 =  {officeName : "毕业班级", children : [], officeType : 3, hiddenId : rTmp.id, label : true};
            children.forEach((c) => {
              if (c.officeType === 3) {
                label1.children.push(c);
              }
            })
            newChildren.push(label1);
          }

          if (has0) {
            children.forEach((c) => {
              if (c.officeType === 0) {
                newSchoolChildren.push(c);
              }
            });
            initOfficeType(newSchoolChildren);
            newChildren = newChildren.concat(newSchoolChildren);
          }
          rTmp.children = newChildren;
        }
      }
    })
  };
  initOfficeType(result);
  return result;
}



//判断是否是数组同时有元素
export function isArrayHas(list) {
  return !(!list || !list.length || list.length < 1);
}

export function officeArrayToTreeForSchool(parentId, officeList) {
  if (!isArrayHas(officeList)) {
    return [];
  }
  let schoolList = [];
  officeList.forEach((oTmp) => {
    if (oTmp.officeType === 0) {
      schoolList.push(oTmp);
    }
  })

  if (!isArrayHas(schoolList)) {
    return [];
  }

  return officeArrayToTree(parentId, schoolList);
}

//机构数组转树型结构，以部门结尾
export function officeArrayToTreeForDept(parentId, officeList) {
  if (!isArrayHas(officeList)) {
    return [];
  }

  let deptList = [];
  officeList.forEach((oTmp) => {
    if (oTmp.officeType === 1) {
      deptList.push(oTmp);
    }
  });

  if (!isArrayHas(deptList)) {
    return [];
  }

  let newOfficeList = [];
  newOfficeList = newOfficeList.concat(deptList);

  let loopList = deptList;
  while (isArrayHas(loopList)) {
    let parentList = [];
    loopList.forEach((lTmp) => {
      for (let i = 0; i < officeList.length; i ++) {
        if (lTmp.parentId === officeList[i].id) {
          parentList.push(officeList[i]);
          break;
        }
      }
    });
    newOfficeList = newOfficeList.concat(parentList);
    loopList = parentList;
  }


  return officeArrayToTree(parentId, newOfficeList);
}

//菜单权限数组转树型结构
export function menuArrayToTree(parentId, officeList) {
  if (!officeList || !officeList.length || officeList.length < 1) {
    return [];
  }
  let result = [];
  for (let i = 0; i < officeList.length; i ++) {
    if (officeList[i].parentId === parentId) {
      result.push(officeList[i]);
      officeList.splice(i, 1);
      i --;
    }

  }
  if (officeList.length > 0) {
    for (let i = 0; i < result.length; i ++) {
      result[i].children = menuArrayToTree(result[i].id, officeList) || [];
    }
  }
  return result;
}
/**
 * 通过meta.role判断是否与当前用户权限匹配
 * @param permissions 权限列表
 * @param route 路由信息
 */
export function hasPermission(permissions, route) {
  // debugger
  //当前路由没有配置需求权限
  if (!route.permission || !route.permission.length || route.permission.length < 1) {
    return true;
  }

  let permAccess = false;
  for (let j = 0; j < route.permission.length; j ++) {
    let matched = false;
    for (let k = 0; k < permissions.length; k ++) {
      if (route.permission[j] === permissions[k]) {
        matched = true;
        break;
      }
    }

    if (route.logical === "and") {
      if (!matched) {
        permAccess = false;
        break;
      } else {
        permAccess = true;
      }
    }
    //logical or
    else {
      if (matched) {
        permAccess = true;
        break;
      }
    }
  }

  return permAccess;
}

/**
 * 根据身份证号获取生日
 * @param idCard
 * @returns {string}
 */
export function getBirth(idCard) {
  var birthday = "";
  if(idCard != null && idCard != ""){
    if(idCard.length == 15){
      birthday = "19"+idCard.slice(6,12);
    } else if(idCard.length == 18){
      birthday = idCard.slice(6,14);
    }
    birthday = birthday.replace(/(.{4})(.{2})/,"$1-$2-");
  }
  return birthday;
}
