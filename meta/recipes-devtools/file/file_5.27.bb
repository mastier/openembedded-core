SUMMARY = "File classification tool"
DESCRIPTION = "File attempts to classify files depending \
on their contents and prints a description if a match is found."
HOMEPAGE = "http://www.darwinsys.com/file/"
SECTION = "console/utils"

# two clause BSD
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;beginline=2;md5=6a7382872edb68d33e1a9398b6e03188"

DEPENDS = "zlib file-replacement-native"
DEPENDS_class-native = "zlib-native"

# Blacklist a bogus tag in upstream check
UPSTREAM_CHECK_GITTAGREGEX = "FILE(?P<pver>(?!6_23).+)"

SRC_URI = "git://github.com/file/file.git \
        file://debian-742262.patch \
        file://0001-Add-P-prompt-into-Usage-info.patch \
        file://host-file.patch \
        "

SRCREV = "72da5ae78ed7b5ecbbe84c36ddb493f7907099bc"
S = "${WORKDIR}/git"

inherit autotools

EXTRA_OEMAKE_append_class-target = "-e FILE_COMPILE=${STAGING_BINDIR_NATIVE}/file-native/file"
EXTRA_OEMAKE_append_class-nativesdk = "-e FILE_COMPILE=${STAGING_BINDIR_NATIVE}/file-native/file"

FILES_${PN} += "${datadir}/misc/*.mgc"

do_install_append_class-native() {
	create_cmdline_wrapper ${D}/${bindir}/file \
		--magic-file ${datadir}/misc/magic.mgc
}

do_install_append_class-nativesdk() {
	create_cmdline_wrapper ${D}/${bindir}/file \
		--magic-file ${datadir}/misc/magic.mgc
}

BBCLASSEXTEND = "native nativesdk"
PROVIDES_append_class-native = " file-replacement-native"
# Don't use NATIVE_PACKAGE_PATH_SUFFIX as that hides libmagic from anyone who
# depends on file-replacement-native.
bindir_append_class-native = "/file-native"
