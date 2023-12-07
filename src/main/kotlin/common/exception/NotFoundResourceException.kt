package common.exception

import java.lang.RuntimeException

class NotFoundResourceException(resourceName: String): RuntimeException("일치하는 ${resourceName}이(갸) 존재하지 않습니다.")