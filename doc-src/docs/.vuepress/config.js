module.exports = {
    base: "/Epsilon/",
    title: 'Epsilon',
    description: 'Epsilon Doc',
    dest: '../docs',
    locales: {
        '/': {
            lang: 'en-US',
            title: 'Epsilon',
            description: 'Fabric Hack Mod'
        },
        '/zh/': {
            lang: 'zh-CN',
            title: 'Epsilon',
            description: 'Fabric作弊模组'
        }
    },
    themeConfig: {
        sidebar: 'auto',
        locales: {
            '/': {
                label: 'English',
                selectText: 'Languages',
                ariaLabel: 'Select language',
                editLinkText: 'Edit this page on GitHub',
                lastUpdated: 'Last Updated',
                nav: require('./nav/en')
            },
            '/zh/': {
                label: '简体中文',
                selectText: '选择语言',
                ariaLabel: '选择语言',
                editLinkText: '在 GitHub 上编辑此页',
                lastUpdated: '上次更新',
                nav: require('./nav/zh'),
            }
        }
    }
}