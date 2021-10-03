import React from 'react';
import { Avatar } from '@mui/material';

export default class UserAvatar extends React.Component {

	stringToColor(string) {
    let hash = 0;

    for (let i = 0; i < string.length; i++) {
      hash = string.charCodeAt(i) + ((hash << 5) - hash);
    }

    let color = '#';

    for (let i = 0; i < 3; i++) {
      const value = (hash >> (i * 8)) & 0xff;
      color += `00${value.toString(16)}`.substr(-2);
    }

    return color;
  }

	stringAvatar(name) {
		return ({
			sx: {
				bgcolor: this.stringToColor(name),
			},
			children: name.substring(1, name.length)
		});
	}

	render() {
		const user = this.props.user;
		return (
			<Avatar {...this.stringAvatar(user.name)} />
		)
	}
}