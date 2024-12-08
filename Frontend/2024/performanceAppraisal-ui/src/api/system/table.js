import request from '@/utils/request'

// 根据员工姓名，查询员工姓名
export function selectNames(deptName) {
  return request({
    url: '/system/table/selectNames',
    method: 'post',
    data:deptName
  })
}

// 页面加载数据 该方法只能放在此处(自定义方法放在方法集合中的第一个)，否则方法不起作用
export function loadData() {
  return request({
    url: '/system/table/loadData',
    method: 'get'
  })
}

// 查询【请填写功能名称】列表
export function listTable(query) {
  return request({
    url: '/system/table/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getTable(deptName, name,time,performance) {
  return request({
    url: '/system/table/queryOneData/' + deptName+'/'+name+'/'+performance+'/'+time,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addTable(data) {
  return request({
    url: '/system/table',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateTable(data) {
  return request({
    url: '/system/table',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delTable(deptName) {
  return request({
    url: '/system/table/' + deptName,
    method: 'delete'
  })
}
