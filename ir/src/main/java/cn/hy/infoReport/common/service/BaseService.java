package cn.hy.infoReport.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * baseService
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BaseService {

    protected static Logger logger = LoggerFactory.getLogger(BaseService.class);

}
