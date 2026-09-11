package com.localcam.pro
import org.junit.Assert.assertEquals
import org.junit.Test
class RoleTest { @Test fun rolesAreAvailable() { assertEquals(Role.CAMERA, Role.valueOf("CAMERA")); assertEquals(Role.MONITOR, Role.valueOf("MONITOR")) } }
