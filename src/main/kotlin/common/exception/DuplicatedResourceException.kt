package common.exception

import java.lang.RuntimeException

class DuplicatedResourceException(resourceName: String) : RuntimeException("${resourceName}이 이미 존재합니다.")