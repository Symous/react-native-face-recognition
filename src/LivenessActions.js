import { Platform } from 'react-native';

export const ActionTypes = Platform.select({
    // iOS、Android原生内不同动作对应的枚举值不相同
    ios: {
        Eye: 0,
        Mouth: 1,
        HeadRight: 2,
        HeadLeft: 3,
        HeadUp: 4,
        HeadDown: 5,
        HeadShake: 6,
    },
    android: {
        Eye: 0,
        Mouth: 1,
        HeadLeft: 2,
        HeadRight: 3,
        HeadShake: 4,
        HeadUp: 5,
        HeadDown: 6,
    },
});

const actions = {
    best: {
        type: Platform.select({
            ios: 'bestImage',
            android: 'bestImage0',
        }),
        name: 'best',
    },
    eye: {
        type: Platform.select({
            ios: 'liveEye',
            android: 'Eye',
        }),
        name: 'eye',
    },
    mouth: {
        type: Platform.select({
            ios: 'liveMouth',
            android: 'Mouth',
        }),
        name: 'mouth',
    },
    headUp: {
        type: Platform.select({
            ios: 'pitchUp',
            android: 'HeadUp',
        }),
        name: 'headUp',
    },
    headDown: {
        type: Platform.select({
            ios: 'pitchDown',
            android: 'HeadDown',
        }),
        name: 'headDown',
    },
    headLeft: {
        type: Platform.select({
            ios: 'yawLeft',
            android: 'HeadLeft',
        }),
        name: 'headLeft',
    },
    headRight: {
        type: Platform.select({
            ios: 'yawRight',
            android: 'HeadRight',
        }),
        name: 'headRight',
    },
    headShake: {
        type: Platform.select({
            ios: 'liveYaw',
            android: 'HeadLeftOrRight',
        }),
        name: 'headShake',
    },
};

export function formatLivenessResult(images) {
    return images.map(item => {
        if (item.type === actions.eye.type) Object.assign(item, { type: actions.eye.name });
        else if (item.type === actions.mouth.type)
            Object.assign(item, { type: actions.mouth.name });
        else if (item.type === actions.headUp.type)
            Object.assign(item, { type: actions.headUp.name });
        else if (item.type === actions.headDown.type)
            Object.assign(item, { type: actions.headDown.name });
        else if (item.type === actions.headLeft.type)
            Object.assign(item, { type: actions.headLeft.name });
        else if (item.type === actions.headRight.type)
            Object.assign(item, { type: actions.headRight.name });
        else if (item.type === actions.headShake.type)
            Object.assign(item, { type: actions.headShake.name });
        else if (item.type === actions.best.type) {
            // iOS SDK返回的是数组，Android返回的是普通对象
            Object.assign(item, { type: actions.best.name });
            // TODO: 会有可能出现bestImage长度没有采集到长度为0的情况吗？
            if (Platform.OS === 'ios') Object.assign(item, { image: item.image[0] });
        }

        return item;
    });
}
