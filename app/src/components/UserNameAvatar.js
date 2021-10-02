import { Avatar } from '@mui/material';
import React from 'react';

export default class UserNameAvatar extends React.Component {
	constructor(props) {
		super(props)

		this.stringToColor = this.stringToColor.bind(this);
		this.stringAvatar = this.stringAvatar.bind(this);
	}

	stringToColor = (string) => {
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
	
	stringAvatar = (name) => {
		return {
			sx: {
				bgcolor: this.stringToColor(name)
			},
			children: `${name.substr(1)}`
		};
	}

	render() {
		const { user } = this.props;
		return (
			<Avatar {...this.stringAvatar(user.name)} />
		)
	}
}